package com.yeldar.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeldar.data.entity.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses")
    fun getCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE id = :id")
    fun getCourseById(id: Int): CourseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCourses(courses: List<CourseEntity>)

    @Query("SELECT * FROM courses WHERE isFavourite = 1")
    fun getFavouriteCourses(): Flow<List<CourseEntity>>

    @Query("UPDATE courses SET isFavourite = :isFavourite WHERE id = :courseId")
    suspend fun updateFavoriteStatus(courseId: Int, isFavourite: Boolean)

    @Query("UPDATE courses SET isFavourite = NOT isFavourite WHERE id == :id")
    suspend fun toggleFavourite(id: Int)
}