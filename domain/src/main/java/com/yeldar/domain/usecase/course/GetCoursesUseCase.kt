package com.yeldar.domain.usecase.course

import com.yeldar.domain.model.Course
import com.yeldar.domain.repository.CourseRepository
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(): List<Course> {
        return repository.getCourses()
    }
}