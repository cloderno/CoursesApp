package com.yeldar.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity (
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: Long,
    val isFavourite: Boolean,
    val publishDate: Long
)