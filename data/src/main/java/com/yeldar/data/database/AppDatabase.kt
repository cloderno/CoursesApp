package com.yeldar.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeldar.data.dao.FavouriteDao
import com.yeldar.data.entity.FavouriteEntity

@Database(
    entities = [
        FavouriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun provideFavouriteDao() : FavouriteDao
}