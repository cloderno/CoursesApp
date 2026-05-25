package com.yeldar.domain.repository

interface AuthRepository {
    fun isUserLoggedIn(): Boolean
    fun setLoggedIn(isLoggedIn: Boolean)
}