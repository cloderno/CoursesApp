package com.yeldar.ui.model

import java.time.Instant
import java.time.LocalDate

data class CourseUi(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: String,
    val isFavorite: Boolean,
    val publishDate: LocalDate
)