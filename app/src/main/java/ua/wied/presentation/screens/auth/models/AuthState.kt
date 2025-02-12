package ua.wied.presentation.screens.auth.models

import androidx.annotation.StringRes

data class AuthState(
    val signIn: SignInState = SignInState(),
    val signUp: SignUpState = SignUpState()
)

data class SignInState (
    val phone: String = "",
    val password: String = ""
)

data class SignUpState(
    val name: String = "",
    val phone: String = "",
    val company: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

data class PageState(
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null
)

