package com.yeldar.home.mapper

import com.yeldar.common.utils.DateUtils
import com.yeldar.domain.model.Course
import com.yeldar.ui.model.CourseUi

fun Course.toUi(): CourseUi {
    return CourseUi(
        id = id,
        title = title,
        description = description,
        price = "$price ₽",
        rating = rating,
        startDate = DateUtils.format(date = startDate.toString()),
        isFavorite = isFavourite,
        publishDate = publishDate
    )
}