package com.yeldar.domain.usecase.favourite

import com.yeldar.domain.model.Course
import com.yeldar.domain.repository.FavouriteRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke(): List<Course> {
        return repository.getFavourites()
    }
}