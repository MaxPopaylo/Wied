package ua.wied.presentation.screens.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.presentation.common.composable.BaseTextField
import ua.wied.presentation.common.composable.PasswordTextField
import ua.wied.presentation.common.composable.PhoneTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.composable.rememberImeState
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.auth.models.SignInUiEvent
import ua.wied.presentation.screens.auth.models.SignUpUiEvent

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = authViewModel.state.signUp
    val isImeVisible by rememberImeState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AnimatedVisibility(!isImeVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        colors.secondaryBackground,
                        RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
                    .padding(top = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .wrapContentHeight(),
                        painter = painterResource(R.drawable.icon_big),
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(R.string.logo)
                    )
                    Text(
                        text = stringResource(R.string.welcome),
                        color = colors.primaryText,
                        style = typography.w400.copy(
                            fontSize = 15.sp
                        )
                    )
                }
            }

        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colors.secondaryBackground,
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                BaseTextField(
                    title = stringResource(R.string.name),
                    text = state.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    errorMessage = state.nameError?.let { stringResource(it) },
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.NameChanged(it))
                    }
                )

                BaseTextField(
                    title = stringResource(R.string.company),
                    text = state.company,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    errorMessage = state.companyError?.let { stringResource(it) },
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.CompanyChanged(it))
                    }
                )

                PhoneTextField (
                    title = stringResource(R.string.phone),
                    text = state.phone,
                    errorMessage = state.phoneError?.let { stringResource(it) },
                    onValueChange = {
                        authViewModel.onEvent(SignInUiEvent.PhoneChanged(it))
                    }
                )

                PasswordTextField(
                    title = stringResource(R.string.password),
                    text = state.password,
                    errorMessage = state.passwordError?.let { stringResource(it) },
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.PasswordChanged(it))
                    }
                )

                PasswordTextField(
                    title = stringResource(R.string.confirm_password),
                    text = state.confirmPassword,
                    errorMessage = state.confirmPasswordError?.let { stringResource(it) },
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.ConfirmPasswordChanged(it))
                    }
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton (
                    title = stringResource(R.string.create_account),
                    onClick = {
                        keyboardController?.hide()
                        authViewModel.onEvent(SignUpUiEvent.SignUpClicked)
                    }
                )

                SecondaryButton (
                    title = stringResource(R.string.login),
                    onClick = {
                        keyboardController?.hide()
                        authViewModel.clearAll()
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
