package ua.wied.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.presentation.screens.auth.models.AuthState
import ua.wied.presentation.screens.auth.models.PageState
import ua.wied.presentation.screens.auth.models.SignInUiEvent
import ua.wied.presentation.screens.auth.models.SignUpUiEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
    private var _state by mutableStateOf(AuthState())
    val state: AuthState get() = _state

    private var _pageState by mutableStateOf(PageState())
    val pageState: PageState get() = _pageState

    private val resultChannel = Channel<AuthResult>()
    val authResult = resultChannel.receiveAsFlow()

    fun onEvent(event: SignInUiEvent) {
        when(event) {
            is SignInUiEvent.PhoneChanged -> {
                _state = _state.copy(
                    signIn = _state.signIn.copy(phone = event.value)
                )
            }
            is SignInUiEvent.PasswordChanged -> {
                _state = _state.copy(
                    signIn = _state.signIn.copy(password = event.value)
                )
            }
            is SignInUiEvent.SignInClicked -> {
                signIn(
                    SignInRequest(
                        phone = _state.signIn.phone,
                        password = _state.signIn.password
                    )
                )
            }
        }
    }

    fun onEvent(event: SignUpUiEvent) {
        when(event) {
            is SignUpUiEvent.NameChanged -> {
                _state = _state.copy(
                    signUp = _state.signUp.copy(name = event.value)
                )
            }
            is SignUpUiEvent.PhoneChanged -> {
                _state = _state.copy(
                    signUp = _state.signUp.copy(phone = event.value)
                )
            }
            is SignUpUiEvent.PasswordChanged -> {
                _state = _state.copy(
                    signUp = _state.signUp.copy(password = event.value)
                )
            }
            is SignUpUiEvent.ConfirmPasswordChanged -> {
                _state = _state.copy(
                    signUp = _state.signUp.copy(confirmPassword = event.value)
                )
            }
            is SignUpUiEvent.CompanyChanged -> {
                _state = _state.copy(
                    signUp = _state.signUp.copy(company = event.value)
                )
            }
            is SignUpUiEvent.SignUpClicked -> {
                signUp(
                    SignUpRequest(
                        name = _state.signUp.name,
                        phone = _state.signUp.phone,
                        password = _state.signUp.password,
                        company = _state.signUp.company
                    )
                )
            }
        }
    }

    private fun signIn(request: SignInRequest) {

    }

    private fun signUp(request: SignUpRequest) {

    }

    fun showErrorDialog(errorMessage: String) {
        _pageState = _pageState.copy(
            errorDialogMessage = errorMessage,
            showErrorDialog = true
        )
    }

    fun clearErrorDialog() {
        clearAll()
        _pageState = _pageState.copy(
            errorDialogMessage = "",
            showErrorDialog = false
        )
    }

    fun showLoadingBar() {
        _pageState = _pageState.copy(
            isLoading = true
        )
    }

    fun clearLoadingBar() {
        _pageState = _pageState.copy(
            isLoading = false
        )
    }

    fun clearAll() {
        _state = AuthState()
    }
}