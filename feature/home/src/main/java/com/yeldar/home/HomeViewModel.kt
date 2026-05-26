package com.yeldar.home

import androidx.annotation.IntDef
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeldar.domain.model.Course
import com.yeldar.domain.usecase.course.GetCoursesUseCase
import com.yeldar.ui.UiState
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
    private val _state = MutableStateFlow<UiState<List<Course>>>(UiState.Loading)
    val state = _state.asStateFlow()

    init {
        android.util.Log.d("HomeViewModel", "ViewModel создана!")
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                delay(3000)
                val courses = getCoursesUseCase()
                _state.value = UiState.Success(courses)
            } catch (e: Exception) {
                _state.value = UiState.Error("Ошибка: ${e.message}")
            }
        }
    }
}