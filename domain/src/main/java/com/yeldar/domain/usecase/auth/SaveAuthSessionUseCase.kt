package com.yeldar.domain.usecase.auth

import com.yeldar.domain.repository.AuthRepository
import javax.inject.Inject

class SaveAuthSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(isLoggedIn: Boolean) = repository.setLoggedIn(isLoggedIn)
}