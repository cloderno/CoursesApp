package com.yeldar.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeldar.domain.usecase.course.GetCoursesUseCase
import com.yeldar.home.mapper.toUi
import com.yeldar.ui.model.CourseUi
import com.yeldar.ui.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase
): ViewModel() {
    private val _state = MutableStateFlow<UiState<List<CourseUi>>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        Log.d("HomeViewModel", "ViewModel создана!")
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                delay(1000) // fake delay to chekc loader
                val courses = getCoursesUseCase()
                _state.value = UiState.Success(courses.map { it.toUi() })
            } catch (e: Exception) {
                _state.value = UiState.Error("Ошибка: ${e.message}")
                Log.e("HomeViewModel", "loadData error: ${e.message}")
            }
        }
    }
}