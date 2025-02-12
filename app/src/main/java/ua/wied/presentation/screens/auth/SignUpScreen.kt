package ua.wied.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.presentation.common.composable.AuthTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.auth.models.SignInUiEvent
import ua.wied.presentation.screens.auth.models.SignUpUiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = authViewModel.state.signUp

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.primaryBackground,
                    titleContentColor = colors.primaryText,
                ),
                title = {
                    Text(
                        text = stringResource(R.string.signup),
                        color = colors.primaryText,
                        style = typography.w700.copy(
                            fontSize = 16.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            authViewModel.clearAll()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_arrow_back),
                            contentDescription = null,
                            tint = colors.primaryText
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primaryBackground)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AuthTextField(
                    title = stringResource(R.string.name),
                    text = state.name,
                    description = stringResource(R.string.name_hint),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.NameChanged(it))
                    }
                )

                AuthTextField(
                    title = stringResource(R.string.company),
                    text = state.company,
                    description = stringResource(R.string.company_hint),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.CompanyChanged(it))
                    }
                )

                AuthTextField(
                    title = stringResource(R.string.phone),
                    text = state.phone,
                    description = stringResource(R.string.phone_hint),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        authViewModel.onEvent(SignInUiEvent.PhoneChanged(it))
                    }
                )

                AuthTextField(
                    title = stringResource(R.string.password),
                    text = state.password,
                    description = stringResource(R.string.enter_password),
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.PasswordChanged(it))
                    }
                )

                AuthTextField(
                    title = stringResource(R.string.confirm_password),
                    text = state.confirmPassword,
                    description = stringResource(R.string.password_again),
                    isPassword = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.ConfirmPasswordChanged(it))
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PrimaryButton (
                    title = stringResource(R.string.signup),
                    onClick = {
                        keyboardController?.hide()
                        authViewModel.onEvent(SignUpUiEvent.SignUpClicked)
                    }
                )
            }

        }
    }
}
