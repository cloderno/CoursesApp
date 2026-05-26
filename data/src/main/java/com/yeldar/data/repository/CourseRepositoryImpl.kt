package com.yeldar.data.repository

import com.yeldar.data.api.CourseApi
import com.yeldar.data.mapper.toDomain
import com.yeldar.domain.model.Course
import com.yeldar.domain.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi
): CourseRepository {
    override suspend fun getCourses(): List<Course> = withContext(Dispatchers.IO) {
        val response = api.getCourses()
        return@withContext response.courses.map { it.toDomain() }
    }
}