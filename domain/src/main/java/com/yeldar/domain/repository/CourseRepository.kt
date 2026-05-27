package com.yeldar.domain.repository

import kotlinx.coroutines.flow.Flow
import com.yeldar.domain.model.Course

interface CourseRepository {
    fun getCourses(): Flow<List<Course>>
    fun getFavouriteCourses(): Flow<List<Course>>
    suspend fun toggleFavorite(courseId: Int, courseIsFavourite: Boolean)
    suspend fun refreshCourses()
}