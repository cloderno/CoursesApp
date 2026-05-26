package com.yeldar.domain.model

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: String,
    val isFavorite: Boolean
)