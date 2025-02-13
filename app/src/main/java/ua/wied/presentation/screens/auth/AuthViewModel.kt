package ua.wied.presentation.screens.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ua.wied.R
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.domain.usecases.SignInUseCase
import ua.wied.domain.usecases.SignUpUseCase
import ua.wied.presentation.common.utils.ToastManager
import ua.wied.presentation.screens.auth.models.AuthState
import ua.wied.presentation.screens.auth.models.PageState
import ua.wied.presentation.screens.auth.models.SignInState
import ua.wied.presentation.screens.auth.models.SignInUiEvent
import ua.wied.presentation.screens.auth.models.SignUpState
import ua.wied.presentation.screens.auth.models.SignUpUiEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private var _state by mutableStateOf(AuthState())
    val state: AuthState get() = _state

    private var _pageState by mutableStateOf(PageState())
    val pageState: PageState get() = _pageState

    private val resultChannel = Channel<AuthResult>()
    val authResult = resultChannel.receiveAsFlow()

    val toastManager = ToastManager()


    fun onEvent(event: SignInUiEvent) {
        when(event) {
            is SignInUiEvent.PhoneChanged -> updateSignInState { copy(phone = event.value) }
            is SignInUiEvent.PasswordChanged -> updateSignInState { copy(password = event.value) }
            is SignInUiEvent.SignInClicked -> {
                if (validateSignIn()) {
                    signIn(
                        SignInRequest(
                            phone = _state.signIn.phone,
                            password = _state.signIn.password
                        )
                    )
                }
            }
        }
    }
    private fun updateSignInState(update: SignInState.() -> SignInState) {
        _state = _state.copy(signIn = _state.signIn.update())
    }

    fun onEvent(event: SignUpUiEvent) {
        when(event) {
            is SignUpUiEvent.NameChanged -> updateSignUpState { copy(name = event.value) }
            is SignUpUiEvent.PhoneChanged -> updateSignUpState { copy(phone = event.value) }
            is SignUpUiEvent.PasswordChanged -> updateSignUpState { copy(password = event.value) }
            is SignUpUiEvent.ConfirmPasswordChanged -> updateSignUpState { copy(confirmPassword = event.value) }
            is SignUpUiEvent.CompanyChanged -> updateSignUpState { copy(company = event.value) }
            is SignUpUiEvent.SignUpClicked -> {
                if (validateSignUp()) {
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
    }
    private fun updateSignUpState(update: SignUpState.() -> SignUpState) {
        _state = _state.copy(signUp = _state.signUp.update())
    }


    private fun validateSignIn(): Boolean {
        with(_state.signIn) {
            updateSignInState {
                copy(
                    phoneError = if (phone.isBlank()) R.string.phone_hint else null,
                    passwordError = when {
                        password.isBlank() -> R.string.enter_password
                        password.length < 8 -> R.string.password_error
                        else -> null
                    }
                )
            }
        }
        return _state.signIn.run { phoneError == null && passwordError == null }
    }
    private fun validateSignUp(): Boolean {
        with(_state.signUp) {
            updateSignUpState {
                copy(
                    nameError = if (name.isBlank()) R.string.name_hint else null,
                    companyError = if (company.isBlank()) R.string.company_hint else null,
                    phoneError = if (phone.isBlank()) R.string.phone_hint else null,
                    passwordError = when {
                        password.isBlank() -> R.string.enter_password
                        password.length < 8 -> R.string.password_error
                        else -> null
                    },
                    confirmPasswordError = if (password != confirmPassword) R.string.password_no_similar else null
                )
            }
        }
        return _state.signUp.run {
            nameError == null &&
                    companyError == null &&
                    phoneError == null &&
                    passwordError == null &&
                    confirmPasswordError == null
        }
    }

    private fun signIn(request: SignInRequest) {
        viewModelScope.launch {
            _pageState = _pageState.copy(isLoading = true)
            resultChannel.send(signInUseCase.invoke(request))
            _pageState = _pageState.copy(isLoading = false)
        }
    }

    private fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            _pageState = _pageState.copy(isLoading = true)
            resultChannel.send(signUpUseCase.invoke(request))
            _pageState = _pageState.copy(isLoading = false)
        }
    }

    fun showErrorToast(@StringRes messageResId: Int) {
        viewModelScope.launch {
            clearAll()
            toastManager.showToast(messageResId)
        }
    }

    fun clearAll() {
        _state = AuthState()
    }
}