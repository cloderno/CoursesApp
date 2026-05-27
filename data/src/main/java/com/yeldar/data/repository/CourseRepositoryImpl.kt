package com.yeldar.data.repository

import com.yeldar.data.api.CourseApi
import com.yeldar.data.dao.CourseDao
import com.yeldar.data.mapper.toDomain
import com.yeldar.data.mapper.toEntity
import com.yeldar.domain.model.Course
import com.yeldar.domain.repository.CourseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject

class CourseRepositoryImpl @Inject constructor(
    private val api: CourseApi,
    private val dao: CourseDao
): CourseRepository {
    override fun getCourses(): Flow<List<Course>> {
        return dao.getCourses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getFavouriteCourses(): Flow<List<Course>> {
        return dao.getFavouriteCourses().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(courseId: Int, courseIsFavourite: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateFavoriteStatus(courseId, !courseIsFavourite)
        }
    }

    override suspend fun refreshCourses() {
        withContext(Dispatchers.IO) {
            try {
                val response  = api.getCourses()

                val entities = response.courses.map { dto ->
                    val localCourse = dao.getCourseById(dto.id)

                    dto.toEntity(localCourse = localCourse)
                }

                dao.saveCourses(entities)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}