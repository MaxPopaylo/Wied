package ua.wied.presentation.screens.people.create.model

import ua.wied.domain.models.user.Role

sealed class CreateEmployeeEvent {
    data class OnLoginChanged(val value: String) : CreateEmployeeEvent()
    data class OnPasswordChanged(val value: String?) : CreateEmployeeEvent()
    data class OnNameChanged(val value: String) : CreateEmployeeEvent()
    data class OnPhoneChanged(val value: String) : CreateEmployeeEvent()
    data class OnRoleChanged(val value: Role) : CreateEmployeeEvent()
    data class OnInfoChanged(val value: String?) : CreateEmployeeEvent()
    data object OnCreate: CreateEmployeeEvent()
}