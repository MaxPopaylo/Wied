package ua.wied.presentation.screens.people.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.models.validation.Validator
import ua.wied.domain.usecases.CreateEmployeeUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.people.create.model.CreateEmployeeEvent
import ua.wied.presentation.screens.people.create.model.CreateEmployeeState
import javax.inject.Inject

@HiltViewModel
class CreateEmployeeViewModel @Inject constructor(
    private val createEmployeeUseCase: CreateEmployeeUseCase,
    private val validator: Validator
): BaseViewModel<CreateEmployeeState, CreateEmployeeEvent>(CreateEmployeeState()) {
    override fun onEvent(event: CreateEmployeeEvent) {
        when (event) {
            is CreateEmployeeEvent.OnLoginChanged -> updateState { it.copy(login = event.value) }
            is CreateEmployeeEvent.OnPasswordChanged -> updateState { it.copy(password = event.value ?: "") }
            is CreateEmployeeEvent.OnNameChanged -> updateState { it.copy(name = event.value) }
            is CreateEmployeeEvent.OnPhoneChanged -> updateState { it.copy(phone = event.value) }
            is CreateEmployeeEvent.OnRoleChanged -> updateState { it.copy(role = event.value) }
            is CreateEmployeeEvent.OnInfoChanged -> updateState { it.copy(info = event.value ?: "") }
            is CreateEmployeeEvent.OnCreate -> createEmployee()
        }
    }

    private fun createEmployee() {
        val state = uiState.value
        if (validateState()) {
            collectNetworkRequest(
                apiCall = {
                    createEmployeeUseCase(
                        login = state.login,
                        password = state.password,
                        name = state.name,
                        phone = state.phone,
                        email = null,
                        role = state.role,
                        info = state.info.takeIf { it.isNotEmpty() }
                    )
                },
                updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
                onFailure = { state.createResult.emit(Result.failure(it)) },
                onSuccess = {
                    state.createResult.emit(Result.success(Unit))
                }
            )
        }
    }

    private fun validateState(): Boolean = with(uiState.value) {
        val validations = listOf(
            validator.validateLoginField(login),
            validator.validatePasswordField(password),
            validator.validateEmptyField(name),
            validator.validatePhoneField(phone)
        )

        updateState {
            it.copy(
                loginError = validations[0].errorMessage,
                passwordError = validations[1].errorMessage,
                nameError = validations[2].errorMessage,
                phoneError = validations[3].errorMessage
            )
        }

        validations.all { it.isSuccessful }
    }
}