package com.yeldar.data.mapper

import com.yeldar.data.dto.CourseDto
import com.yeldar.data.entity.FavouriteEntity
import com.yeldar.domain.model.Course
import java.time.Instant
import com.yeldar.common.utils.toEpochMilli
import java.time.LocalDate

fun Course.toEntity(): FavouriteEntity {
    return FavouriteEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        price = this.price,
        rating = this.rating,
        startDate = this.startDate.toEpochMilli(),
        isFavorite = this.isFavorite,
        publishDate = this.publishDate.toEpochMilli()
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
        isFavorite = this.hasLike,
        publishDate = LocalDate.parse(this.publishDate)
    )
}

fun CourseDto.toFavouriteEntity(): FavouriteEntity {
    val startLocalDate = LocalDate.parse(this.startDate)
    val publishLocalDate = LocalDate.parse(this.publishDate)

    return FavouriteEntity(
        id = this.id,
        title = this.title,
        description = this.text,
        price = this.price,
        rating = this.rate,
        startDate = startLocalDate.toEpochMilli(),
        isFavorite = this.hasLike,
        publishDate = publishLocalDate.toEpochMilli()
    )
}