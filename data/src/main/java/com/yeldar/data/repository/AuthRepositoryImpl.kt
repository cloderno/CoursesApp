package com.yeldar.data.repository

import android.content.SharedPreferences
import com.yeldar.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences
): AuthRepository {
    override fun isUserLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    override fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    companion object {
        private const val KEY_IS_LOGGED_IN = "key_is_logged_in"
    }
}