package ua.wied.presentation.screens.people.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ua.wied.R
import ua.wied.domain.models.user.Role
import ua.wied.presentation.common.composable.BaseTextField
import ua.wied.presentation.common.composable.PasswordTextField
import ua.wied.presentation.common.composable.PhoneTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.dropdowns.TypeDropdown
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.utils.extensions.positionAwareImePadding
import ua.wied.presentation.screens.people.create.model.CreateEmployeeEvent
import ua.wied.presentation.screens.people.create.model.CreateEmployeeState

@Composable
fun CreateEmployeeScreen(
    state: CreateEmployeeState,
    onEvent: (CreateEmployeeEvent) -> Unit,
    backToPeopleScreen: (Boolean) -> Unit
) {
    val isButtonEnabled = listOf(
        state.login,
        state.password,
        state.name,
        state.phone,
    ).all { it.isNotEmpty() }
    val scrollState = rememberScrollState()

    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = { backToPeopleScreen(true) },
                onFailure = {

                }
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(top = dimen.containerPaddingLarge)
            .positionAwareImePadding()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {

        BaseTextField(
            title = stringResource(R.string.employee_login_hint),
            text = state.login,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            errorMessage = state.loginError,
            onValueChange = {
                onEvent(CreateEmployeeEvent.OnLoginChanged(it))
            }
        )

        PasswordTextField(
            title = stringResource(R.string.employee_password_hint),
            text = state.password,
            errorMessage = state.passwordError,
            onValueChange = {
                onEvent(CreateEmployeeEvent.OnPasswordChanged(it))
            }
        )

        BaseTextField(
            title = stringResource(R.string.employee_name_hint),
            text = state.name,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            errorMessage = state.nameError,
            onValueChange = {
                onEvent(CreateEmployeeEvent.OnNameChanged(it))
            }
        )

        PhoneTextField(
            title = stringResource(R.string.employee_phone_hint),
            text = state.phone,
            errorMessage = state.phoneError,
            onValueChange = {
                onEvent(CreateEmployeeEvent.OnPhoneChanged(it))
            }
        )

        TypeDropdown(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.language),
            types = Role.entries.toList().drop(1),
            iconColor = colors.primaryText,
            initType = state.role,
            onSelected = {
                onEvent(CreateEmployeeEvent.OnRoleChanged(it))
            }
        )

        Spacer(modifier = Modifier.height(dimen.padding2Xl))

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimen.padding2Xl).positionAwareImePadding(),
            title = stringResource(R.string.create),
            onClick = {
                onEvent(CreateEmployeeEvent.OnCreate)
            },
            isEnabled = isButtonEnabled
        )
    }
}