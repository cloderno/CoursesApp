package com.yeldar.domain.repository

import com.yeldar.domain.model.Course

interface FavouriteRepository {
    suspend fun getFavourites(): List<Course>
    suspend fun saveFavourite(course: Course)
}