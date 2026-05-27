package com.yeldar.domain.usecase.course

import com.yeldar.domain.repository.CourseRepository
import javax.inject.Inject

class RefreshCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke() {
        repository.refreshCourses()
    }
}