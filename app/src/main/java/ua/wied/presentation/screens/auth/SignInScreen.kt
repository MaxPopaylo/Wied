package ua.wied.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.navigation.AuthNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.auth.models.SignInUiEvent

@Composable
fun SignInScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val state = authViewModel.state.signIn
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .padding(16.dp)
            .padding(top = 32.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
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
                fontSize = 16.sp
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
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
                    authViewModel.onEvent(SignInUiEvent.PasswordChanged(it))
                }
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            PrimaryButton (
                title = stringResource(R.string.login),
                onClick = {
                    authViewModel.onEvent(SignInUiEvent.SignInClicked)
                }
            )

            SecondaryButton (
                title = stringResource(R.string.signup),
                onClick = {
                    authViewModel.clearAll()
                    navController.navigate(AuthNav.SignUp.route)
                }
            )
        }
    }
}