package com.yeldar.domain.model

import java.time.Instant

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: Instant,
    val isFavorite: Boolean,
    val publishDate: Instant
)