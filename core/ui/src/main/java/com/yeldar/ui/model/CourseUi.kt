package com.yeldar.ui.model

data class CourseUi(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: String,
    val isFavorite: Boolean
)