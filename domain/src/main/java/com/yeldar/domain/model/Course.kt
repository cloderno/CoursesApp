package com.yeldar.domain.model

import java.time.LocalDate

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val startDate: LocalDate,
    val isFavorite: Boolean,
    val publishDate: LocalDate
)
