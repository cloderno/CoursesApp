package com.yeldar.navigation

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

object Screens {
    private const val BASE_URL = "app://courses"

    const val LOGIN_DESTINATION = "$BASE_URL/login"
    const val HOME_DESTINATION = "$BASE_URL/home"
    const val FAVOURITES_DESTINATION = "$BASE_URL/favorites"

    fun createDeeLinkRequest(destination: String): NavDeepLinkRequest {
        return NavDeepLinkRequest.Builder
            .fromUri(destination.toUri())
            .build()
    }
}