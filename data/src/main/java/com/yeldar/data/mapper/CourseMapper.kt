package com.yeldar.data.mapper

import com.yeldar.data.dto.CourseDto
import com.yeldar.data.entity.CourseEntity
import com.yeldar.domain.model.Course
import com.yeldar.common.utils.toEpochMilli
import java.time.Instant
import java.time.LocalDate

fun CourseEntity.toDomain(): Course {
    val startLocalDate = Instant.ofEpochMilli(this.startDate).atZone(java.time.ZoneId.systemDefault()).toLocalDate()
    val publishLocalDate = Instant.ofEpochMilli(this.publishDate).atZone(java.time.ZoneId.systemDefault()).toLocalDate()

    return Course(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        rating = this.rating,
        startDate = startLocalDate,
        isFavourite = this.isFavourite,
        publishDate = publishLocalDate
    )
}

fun CourseDto.toDomain(): Course {
    return Course(
        id = this.id,
        title = this.title,
        description = this.text,
        price = this.price,
        rating = this.rate,
        startDate = LocalDate.parse(this.startDate),
        isFavourite = this.hasLike,
        publishDate = LocalDate.parse(this.publishDate)
    )
}

fun CourseDto.toEntity(localCourse: CourseEntity?): CourseEntity {
    val startLocalDate = LocalDate.parse(this.startDate)
    val publishLocalDate = LocalDate.parse(this.publishDate)
    val finalFavourite = localCourse?.isFavourite ?: this.hasLike

    return CourseEntity(
        id = this.id,
        title = this.title,
        description = this.text,
        price = this.price,
        rating = this.rate,
        startDate = startLocalDate.toEpochMilli(),
        isFavourite = finalFavourite,
        publishDate = publishLocalDate.toEpochMilli()
    )
}