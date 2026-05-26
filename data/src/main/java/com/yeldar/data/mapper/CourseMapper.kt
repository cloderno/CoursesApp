package com.yeldar.data.mapper

import com.yeldar.data.dto.CourseDto
import com.yeldar.domain.model.Course

fun CourseDto.toDomain(): Course {
    return Course(
        id = this.id,
        title = this.title,
        description = this.text,
        price = "${this.price} ₽",
        rating = this.rate.toString(),
        startDate = this.startDate,
        isFavorite = this.hasLike
    )
}