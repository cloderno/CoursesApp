package com.yeldar.domain.usecase.course

import com.yeldar.domain.model.Course
import com.yeldar.domain.repository.CourseRepository
import javax.inject.Inject

class ToggleFavouriteCourseUseCase @Inject constructor(
    private val repository: CourseRepository
) {
    suspend operator fun invoke(courseId: Int, courseIsFavourite: Boolean) {
        repository.toggleFavorite(courseId, courseIsFavourite)
    }
}

