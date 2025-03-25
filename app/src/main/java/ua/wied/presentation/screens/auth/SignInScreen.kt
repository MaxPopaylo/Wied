package ua.wied.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
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

    val enableButton = authViewModel.isFieldsEmpty(state.login, state.password)

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
                .fillMaxHeight(0.2f)
                .background(
                    colors.secondaryBackground
                )
                .padding(16.dp)
                .padding(bottom = 12.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                    modifier = Modifier.padding(top = 4.dp),
                    text = stringResource(R.string.welcome),
                    color = colors.primaryText,
                    style = typography.w500.copy(
                        fontSize = 15.sp
                    )
                )
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            PrimaryButton (
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.signin),
                onClick = {
                    keyboardController?.hide()
                }
            )

            Spacer(modifier = Modifier.width(10.dp))

            SecondaryButton (
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.signup),
                onClick = {
                    keyboardController?.hide()
                    authViewModel.clearAll()
                    navController.navigate(AuthNav.SignUp)
                }
            )
        }

        Column (
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    colors.primaryBackground
                )
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            BaseTextField(
                title = stringResource(R.string.login),
                text = state.login,
                fieldColor = colors.secondaryBackground,
                errorMessage = state.loginError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                onValueChange = {
                    authViewModel.onEvent(SignInUiEvent.LoginChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField (
                title = stringResource(R.string.password),
                text = state.password,
                fieldColor = colors.secondaryBackground,
                textButton = stringResource(R.string.forgot_password_answer),
                errorMessage = state.passwordError,
                onTextButtonClick = {},
                onValueChange = {
                    authViewModel.onEvent(SignInUiEvent.PasswordChanged(it))
                }
            )

            Spacer(modifier = Modifier.height(52.dp))

            PrimaryButton (
                title = stringResource(R.string.signin),
                isEnabled = enableButton,
                onClick = {
                    keyboardController?.hide()
                    authViewModel.onEvent(SignInUiEvent.SignInClicked)
                }
            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    colors.secondaryBackground
                )
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.height(1.5.dp).width(60.dp),
                    color = colors.primaryText
                )
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    text = "OR",
                    color = colors.primaryText,
                    style = typography.w500.copy(
                        fontSize = 16.sp
                    )
                )
                HorizontalDivider(
                    modifier = Modifier.height(1.5.dp).width(60.dp),
                    color = colors.primaryText
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SignInIntegrationButton(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(R.drawable.icon_google),
                    description = stringResource(R.string.google),
                    onClick = {
                        authViewModel.showErrorToast(R.string.coming_soon)
                    }
                )
                SignInIntegrationButton(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(R.drawable.icon_facebook),
                    description = stringResource(R.string.facebook),
                    onClick = {
                        authViewModel.showErrorToast(R.string.coming_soon)
                    }
                )
                SignInIntegrationButton(
                    modifier = Modifier.weight(1f),
                    icon = painterResource(R.drawable.icon_apple),
                    description = stringResource(R.string.apple),
                    onClick = {
                        authViewModel.showErrorToast(R.string.coming_soon)
                    }
                )
            }



        }
    }
}


@Composable
private fun SignInIntegrationButton(
    modifier: Modifier = Modifier,
    description: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(
                colors.primaryBackground,
                RoundedCornerShape(4.dp)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = icon,
            contentDescription = description
        )
    }
}