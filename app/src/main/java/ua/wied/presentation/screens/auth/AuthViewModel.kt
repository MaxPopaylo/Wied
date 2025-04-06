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
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.domain.models.user.Role
import ua.wied.domain.models.validation.Validator
import ua.wied.domain.usecases.SignInUseCase
import ua.wied.domain.usecases.SignUpUseCase
import ua.wied.presentation.common.utils.ToastManager
import ua.wied.presentation.screens.auth.models.AuthState
import ua.wied.presentation.screens.auth.models.SignInState
import ua.wied.presentation.screens.auth.models.SignInUiEvent
import ua.wied.presentation.screens.auth.models.SignUpState
import ua.wied.presentation.screens.auth.models.SignUpUiEvent
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val validator: Validator
) : ViewModel() {
    private var _state by mutableStateOf(AuthState())
    val state: AuthState get() = _state

    private val resultChannel = Channel<AuthResult>()
    val authResult = resultChannel.receiveAsFlow()

    val toastManager = ToastManager()


    fun onEvent(event: SignInUiEvent) {
        when(event) {
            is SignInUiEvent.LoginChanged -> updateSignInState { copy(login = event.value) }
            is SignInUiEvent.PasswordChanged -> updateSignInState { copy(password = event.value) }
            is SignInUiEvent.SignInClicked -> {
                if (validateSignIn()) {
                    signIn(
                        SignInRequest(
                            login = _state.signIn.login,
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
            is SignUpUiEvent.LoginChanged -> updateSignUpState { copy(login = event.value) }
            is SignUpUiEvent.NameChanged -> updateSignUpState { copy(name = event.value) }
            is SignUpUiEvent.PhoneChanged -> updateSignUpState { copy(phone = event.value) }
            is SignUpUiEvent.EmailChanged -> updateSignUpState { copy(email = event.value) }
            is SignUpUiEvent.CompanyChanged -> updateSignUpState { copy(company = event.value) }
            is SignUpUiEvent.PasswordChanged -> updateSignUpState { copy(password = event.value) }
            is SignUpUiEvent.ConfirmPasswordChanged -> updateSignUpState { copy(confirmPassword = event.value) }
            is SignUpUiEvent.SignUpClicked -> {
                if (validateSignUp()) {
                    with(_state.signUp) {
                        signUp(
                            SignUpRequest(
                                login = login,
                                name = name,
                                phone = phone,
                                email = email,
                                company = company,
                                password = password,
                                info = "",
                                role = Role.OWNER
                            )
                        )
                    }
                }
            }
        }
    }
    private fun updateSignUpState(update: SignUpState.() -> SignUpState) {
        _state = _state.copy(signUp = _state.signUp.update())
    }


    private fun validateSignIn(): Boolean = with(_state.signIn) {
        val loginValidation = validator.validateEmptyField(login)
        val passwordValidation = validator.validateEmptyField(password)

        updateSignInState {
            copy(
                loginError = loginValidation.errorMessage,
                passwordError = passwordValidation.errorMessage
            )
        }

        return loginValidation.isSuccessful && passwordValidation.isSuccessful
    }

    private fun validateSignUp(): Boolean = with(_state.signUp) {
        val validations = listOf(
            validator.validateLoginField(login),
            validator.validateEmptyField(name),
            validator.validatePhoneField(phone),
            validator.validateEmailField(email),
            validator.validateCompanyField(company),
            validator.validatePasswordField(password),
            validator.validatePasswordConfirmField(password, confirmPassword)
        )

        updateSignUpState {
            copy(
                loginError = validations[0].errorMessage,
                nameError = validations[1].errorMessage,
                phoneError = validations[2].errorMessage,
                emailError = validations[3].errorMessage,
                companyError = validations[4].errorMessage,
                passwordError = validations[5].errorMessage,
                confirmPasswordError = validations[6].errorMessage
            )
        }

        validations.all { it.isSuccessful }
    }

    fun isFieldsEmpty(vararg fields: String): Boolean = fields
        .all { validator.validateEmptyField(it).isSuccessful }

    private fun signIn(request: SignInRequest) {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            resultChannel.send(signInUseCase.invoke(request))
            _state = _state.copy(isLoading = false)
        }
    }

    private fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            _state = _state.copy(isLoading = true)
            resultChannel.send(signUpUseCase.invoke(request))
            _state = _state.copy(isLoading = false)
        }
    }

    fun showErrorToast(@StringRes messageResId: Int) {
        viewModelScope.launch {
            toastManager.showToast(messageResId)
        }
    }

    fun clearAll() {
        _state = AuthState()
    }
}