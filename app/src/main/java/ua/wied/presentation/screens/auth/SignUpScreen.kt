package ua.wied.presentation.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.presentation.common.composable.BaseTextField
import ua.wied.presentation.common.composable.PasswordTextField
import ua.wied.presentation.common.composable.PhoneTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.navigation.AuthNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.auth.models.SignUpUiEvent

@Composable
fun SignUpScreen(
    authViewModel: AuthViewModel,
    navController: NavHostController,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = authViewModel.state.signUp
    val scrollState = rememberScrollState()

    val enableButton = state.phone.isNotEmpty() && state.password.isNotEmpty() &&
                        state.company.isNotEmpty() && state.confirmPassword.isNotEmpty()

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
                .padding(16.dp)
                .padding(top = 12.dp),
            contentAlignment = Alignment.Center
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
                    text = stringResource(R.string.welcome),
                    color = colors.primaryText,
                    style = typography.w500.copy(
                        fontSize = 15.sp
                    )
                )
            }
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .positionAwareImePadding()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row {
                    SecondaryButton (
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        title = stringResource(R.string.signin),
                        onClick = {
                            keyboardController?.hide()
                            authViewModel.clearAll()
                            navController.navigate(AuthNav.SignIn.route)
                        }
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    PrimaryButton (
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        title = stringResource(R.string.signup),
                        onClick = {
                            keyboardController?.hide()
                        }
                    )
                }

                Column(
                    Modifier.verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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

                    PhoneTextField(
                        title = stringResource(R.string.phone),
                        text = state.phone,
                        errorMessage = state.phoneError?.let { stringResource(it) },
                        onValueChange = {
                            authViewModel.onEvent(SignUpUiEvent.PhoneChanged(it))
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
            }

            PrimaryButton(
                title = stringResource(R.string.create_account),
                isEnabled = enableButton,
                onClick = {
                    keyboardController?.hide()
                    authViewModel.onEvent(SignUpUiEvent.SignUpClicked)
                }
            )

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(
                    colors.secondaryBackground
                )
        )

    }
}

fun Modifier.positionAwareImePadding() = composed {
    var consumePadding by remember { mutableIntStateOf(0) }
    this@positionAwareImePadding
        .onGloballyPositioned { coordinates ->
            val rootCoordinate = coordinates.findRootCoordinates()
            val bottom = coordinates.positionInWindow().y + coordinates.size.height

            consumePadding = (rootCoordinate.size.height - bottom).toInt()
        }
        .consumeWindowInsets(PaddingValues(bottom = consumePadding.pxToDp()))
        .imePadding()
}

@Composable
fun Int.pxToDp(): Dp {
    return with(LocalDensity.current) { this@pxToDp.toDp() }
}