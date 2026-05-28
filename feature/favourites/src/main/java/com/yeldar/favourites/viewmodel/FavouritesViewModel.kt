package com.yeldar.favourites.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.yeldar.domain.usecase.course.GetFavouriteCoursesUseCase
import com.yeldar.domain.usecase.course.ToggleFavouriteCourseUseCase
import com.yeldar.ui.model.CourseUi
import com.yeldar.ui.model.UiState
import androidx.lifecycle.viewModelScope
import com.yeldar.favourites.mapper.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouriteCoursesUseCase: GetFavouriteCoursesUseCase,
    private val toggleFavouriteCourseUseCase: ToggleFavouriteCourseUseCase
): ViewModel() {
    private val _state = MutableStateFlow<List<CourseUi>>(emptyList())
    val state = _state.asStateFlow()

    init {
        observeCourses()
    }

    fun observeCourses() {
        viewModelScope.launch {
            getFavouriteCoursesUseCase().collect { favourites ->
                _state.value = favourites.map { it.toUi() }
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