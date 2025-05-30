package ua.wied.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.presentation.common.composable.BaseTextField
import ua.wied.presentation.common.composable.PasswordTextField
import ua.wied.presentation.common.composable.PhoneTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.navigation.AuthNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.positionAwareImePadding
import ua.wied.presentation.screens.auth.models.SignUpUiEvent

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = authViewModel.state.signUp
    val scrollState = rememberScrollState()

    val enableButton = with(state) {authViewModel.isFieldsEmpty(login, name, phone, email, company, password, confirmPassword)}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .fillMaxHeight(0.2f)
                .background(
                    colors.secondaryBackground
                )
                .padding(dimen.containerPadding)
                .padding(bottom = dimen.paddingL),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .wrapContentHeight(),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_big),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.logo)
                )
                Text(
                    modifier = Modifier.padding(top = dimen.padding2Xs),
                    text = stringResource(R.string.welcome),
                    style = typography.body1.copy(
                        fontWeight = FontWeight.W500
                    )
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .positionAwareImePadding()
                .padding(horizontal = dimen.padding2Xl, vertical = dimen.paddingLarge)
        ) {
            Row {
                SecondaryButton (
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    title = stringResource(R.string.signin),
                    onClick = {
                        keyboardController?.hide()
                        authViewModel.clearAll()
                        navController.navigate(AuthNav.SignIn)
                    }
                )

                Spacer(modifier = Modifier.width(dimen.paddingM))

                PrimaryButton (
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    title = stringResource(R.string.signup),
                    onClick = {
                        keyboardController?.hide()
                    }
                )
            }

            Spacer(modifier = Modifier.height(dimen.padding2Xl))

            Column(
                Modifier.weight(1f).verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
            ) {
                BaseTextField(
                    title = stringResource(R.string.login_hint),
                    text = state.login,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    errorMessage = state.loginError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.LoginChanged(it))
                    }
                )

                BaseTextField(
                    title = stringResource(R.string.name_hint),
                    text = state.name,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    errorMessage = state.nameError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.NameChanged(it))
                    }
                )

                PhoneTextField(
                    title = stringResource(R.string.phone_hint),
                    text = state.phone,
                    errorMessage = state.phoneError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.PhoneChanged(it))
                    }
                )

                BaseTextField(
                    title = stringResource(R.string.email_hint),
                    text = state.email,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    errorMessage = state.emailError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.EmailChanged(it))
                    }
                )

                BaseTextField(
                    title = stringResource(R.string.company_hint),
                    text = state.company,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    errorMessage = state.companyError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.CompanyChanged(it))
                    }
                )

                PasswordTextField(
                    title = stringResource(R.string.password),
                    text = state.password,
                    errorMessage = state.passwordError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.PasswordChanged(it))
                    }
                )

                PasswordTextField(
                    title = stringResource(R.string.confirm_password),
                    text = state.confirmPassword,
                    errorMessage = state.confirmPasswordError,
                    onValueChange = {
                        authViewModel.onEvent(SignUpUiEvent.ConfirmPasswordChanged(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(dimen.padding2Xl))

            PrimaryButton(
                title = stringResource(R.string.create_account),
                isEnabled = enableButton,
                onClick = {
                    keyboardController?.hide()
                    authViewModel.onEvent(SignUpUiEvent.SignUpClicked)
                }
            )

        }
    }
}
