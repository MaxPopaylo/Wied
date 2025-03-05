package ua.wied.presentation.screens.auth.models

data class AuthState(
    val signIn: SignInState = SignInState(),
    val signUp: SignUpState = SignUpState()
)

data class SignInState(
    val login: String = "",
    val password: String = "",

    val loginError: String? = null,
    val passwordError: String? = null
)

data class SignUpState(
    val login: String = "",
    val name: String = "",
    val phone: String = "",
    val email: String = "",
    val company: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val loginError: String? = null,
    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val companyError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
)

data class PageState(
    val isLoading: Boolean = false
)

