package com.yeldar.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: Long,
    val isFavorite: Boolean,
    val publishDate: Long
)