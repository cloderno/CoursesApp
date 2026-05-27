package com.yeldar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeldar.data.dao.CourseDao
import com.yeldar.data.entity.CourseEntity

@Database(
    entities = [
        CourseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun provideCourseDao() : CourseDao
}