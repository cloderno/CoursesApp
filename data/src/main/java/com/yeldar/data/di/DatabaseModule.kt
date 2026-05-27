package com.yeldar.data.di

import android.content.Context
import androidx.room.Room
import com.yeldar.data.dao.CourseDao
import com.yeldar.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "courses_database"
        ).build()
    }

    @Provides
    fun provideCourseDao(database: AppDatabase): CourseDao {
        return database.provideCourseDao()
    }
}