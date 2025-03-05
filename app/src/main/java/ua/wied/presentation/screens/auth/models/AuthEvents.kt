package ua.wied.presentation.screens.auth.models

sealed class SignInUiEvent {
    data class LoginChanged(val value: String): SignInUiEvent()
    data class PasswordChanged(val value: String): SignInUiEvent()
    data object SignInClicked : SignInUiEvent()
}

sealed class SignUpUiEvent {
    data class LoginChanged(val value: String): SignUpUiEvent()
    data class NameChanged(val value: String): SignUpUiEvent()
    data class PhoneChanged(val value: String): SignUpUiEvent()
    data class EmailChanged(val value: String): SignUpUiEvent()
    data class CompanyChanged(val value: String): SignUpUiEvent()
    data class PasswordChanged(val value: String): SignUpUiEvent()
    data class ConfirmPasswordChanged(val value: String): SignUpUiEvent()
    data object SignUpClicked : SignUpUiEvent()
}

