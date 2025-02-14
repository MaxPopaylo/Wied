package ua.wied.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.presentation.common.composable.PasswordTextField
import ua.wied.presentation.common.composable.PhoneTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.navigation.AuthNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.auth.models.SignInUiEvent

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = authViewModel.state.signIn

    val enableButton = state.phone.isNotEmpty() && state.password.isNotEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
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

        Column (
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    colors.secondaryBackground,
                    RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                PhoneTextField (
                    title = stringResource(R.string.phone),
                    text = state.phone,
                    errorMessage = state.phoneError?.let { stringResource(it) },
                    onValueChange = {
                        authViewModel.onEvent(SignInUiEvent.PhoneChanged(it))
                    }
                )

                PasswordTextField (
                    title = stringResource(R.string.password),
                    text = state.password,
                    textButton = stringResource(R.string.forgot_password_answer),
                    description = stringResource(R.string.enter_password),
                    errorMessage = state.passwordError?.let { stringResource(it) },
                    onTextButtonClick = {},
                    onValueChange = {
                        authViewModel.onEvent(SignInUiEvent.PasswordChanged(it))
                    }
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PrimaryButton (
                    title = stringResource(R.string.login),
                    isEnabled = enableButton,
                    onClick = {
                        keyboardController?.hide()
                        authViewModel.onEvent(SignInUiEvent.SignInClicked)
                    }
                )

                SecondaryButton (
                    title = stringResource(R.string.signup),
                    onClick = {
                        keyboardController?.hide()
                        authViewModel.clearAll()
                        navController.navigate(AuthNav.SignUp.route)
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    colors.secondaryBackground,
                    RoundedCornerShape(12.dp)
                )
        )
    }
}