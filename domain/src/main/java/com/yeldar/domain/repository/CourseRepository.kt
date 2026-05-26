package com.yeldar.domain.repository

import com.yeldar.domain.model.Course

interface CourseRepository {
    suspend fun getCourses(): List<Course>
}