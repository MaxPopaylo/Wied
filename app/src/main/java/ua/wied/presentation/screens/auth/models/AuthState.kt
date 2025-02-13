package ua.wied.presentation.screens.auth.models

import androidx.annotation.StringRes

data class AuthState(
    val signIn: SignInState = SignInState(),
    val signUp: SignUpState = SignUpState()
)

data class SignInState (
    val phone: String = "",
    val password: String = "",

    @StringRes val phoneError: Int? = null,
    @StringRes val passwordError: Int? = null
)

data class SignUpState(
    val name: String = "",
    val phone: String = "",
    val company: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    @StringRes val nameError: Int? = null,
    @StringRes val phoneError: Int? = null,
    @StringRes val companyError: Int? = null,
    @StringRes val passwordError: Int? = null,
    @StringRes val confirmPasswordError: Int? = null
)

data class PageState(
    val isLoading: Boolean = false
)

