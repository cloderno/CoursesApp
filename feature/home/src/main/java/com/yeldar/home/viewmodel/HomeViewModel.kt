package com.yeldar.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeldar.domain.usecase.course.GetCoursesUseCase
import com.yeldar.domain.usecase.course.RefreshCoursesUseCase
import com.yeldar.domain.usecase.course.ToggleFavouriteCourseUseCase
import com.yeldar.home.mapper.toUi
import com.yeldar.ui.model.CourseUi
import com.yeldar.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val refreshCoursesUseCase: RefreshCoursesUseCase,
    private val toggleFavouriteCourseUseCase: ToggleFavouriteCourseUseCase
): ViewModel() {
    private val _state = MutableStateFlow<UiState<List<CourseUi>>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        Log.d("HomeViewModel", "ViewModel создана!")
        observeCourses()
        refreshData()
    }

    private fun observeCourses() {
        viewModelScope.launch {
            getCoursesUseCase()
                .catch { e ->
                    _state.value = UiState.Error("Ошибка БД: ${e.message}")
                    Log.e("HomeViewModel", "observeCourses error: ${e.message}")
                }
                .collect { domainCourses ->
                    if (domainCourses.isEmpty()) {
                        _state.value = UiState.Loading
                    } else {
                        val uiCourses = domainCourses.map { it.toUi() }
                        _state.value = UiState.Success(uiCourses)
                    }
                }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                refreshCoursesUseCase()
            } catch (e: Exception) {
                Log.e("HomeViewModel", "refreshData error (сеть): ${e.message}")
            }
        }
    }

    fun onFavoriteClick(course: CourseUi) {
        viewModelScope.launch {
            try {
                toggleFavouriteCourseUseCase(course.id, course.isFavorite)
                // Никакого ручного обновления стейта делать НЕ НАДО!
                // Репозиторий обновит Room, Room пнет Flow, и всё перерисуется само.
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Toggle favorite error: ${e.message}")
            }
        }
    }
}