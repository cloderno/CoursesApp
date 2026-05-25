package com.yeldar.domain.model

data class Course(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val isFavorite: Boolean
)