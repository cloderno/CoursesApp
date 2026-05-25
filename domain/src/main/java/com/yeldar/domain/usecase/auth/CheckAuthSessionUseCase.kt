package com.yeldar.domain.usecase.auth

import com.yeldar.domain.repository.AuthRepository
import javax.inject.Inject

class CheckAuthSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Boolean = repository.isUserLoggedIn()
}