package com.yeldar.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeldar.domain.model.SortOrder
import com.yeldar.domain.usecase.course.GetCoursesAscUseCase
import com.yeldar.domain.usecase.course.GetCoursesDescUseCase
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val refreshCoursesUseCase: RefreshCoursesUseCase,
    private val getCoursesAscUseCase: GetCoursesAscUseCase,
    private val getCoursesDescUseCase: GetCoursesDescUseCase,
    private val toggleFavouriteCourseUseCase: ToggleFavouriteCourseUseCase
): ViewModel() {
    private val _state = MutableStateFlow<UiState<List<CourseUi>>>(UiState.Loading)
    val state = _state.asStateFlow()

    private val _sortOrder = MutableStateFlow(SortOrder.NONE)
    val sortOrder = _sortOrder.asStateFlow()

    init {
        Log.d("HomeViewModel", "ViewModel создана!")
        observeCourses()
        refreshData()
    }

    private fun observeCourses() {
        viewModelScope.launch {
            _sortOrder
                .flatMapLatest { order ->
                    when (order) {
                        SortOrder.ASC -> getCoursesAscUseCase()
                        SortOrder.DESC -> getCoursesDescUseCase()
                        SortOrder.NONE -> getCoursesUseCase()
                    }
                }
                .catch { e ->
                    _state.value = UiState.Error("Ошибка: ${e.message}")
                }
                .collect { domainCourses ->
                    _state.value = UiState.Success(domainCourses.map { it.toUi() })
                }
        }
    }

    fun toggleSort() {
        _sortOrder.value = when (_sortOrder.value) {
            SortOrder.NONE -> SortOrder.DESC
            SortOrder.DESC -> SortOrder.ASC
            SortOrder.ASC -> SortOrder.DESC
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
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Toggle favorite error: ${e.message}")
            }
        }
    }
}