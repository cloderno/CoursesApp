package com.yeldar.domain.usecase.course

import com.yeldar.domain.model.Course
import com.yeldar.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouriteCoursesUseCase @Inject constructor(
    private val repository: CourseRepository
) {
     operator fun invoke(): Flow<List<Course>> {
        return repository.getFavouriteCourses()
     }
}