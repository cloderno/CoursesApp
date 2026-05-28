package com.yeldar.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yeldar.common.utils.isValidEmail
import com.yeldar.domain.usecase.auth.SaveAuthSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val saveAuthSessionUseCase: SaveAuthSessionUseCase
): ViewModel() {
    val emailFlow = MutableStateFlow("")
    val passwordFlow = MutableStateFlow("")

    val isLoginButtonEnabled: StateFlow<Boolean> = combine(emailFlow, passwordFlow) { email, password ->
        val isValidEmail = isValidEmail(email)
        val isFieldsNotEmpty = email.isNotBlank() && password.isNotBlank()

        isValidEmail && isFieldsNotEmpty
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    // Сохраняем сессию в SharedPreferences через UseCase из домена
    fun onLoginClicked() {
        saveAuthSessionUseCase(isLoggedIn = true)
    }
}