package ua.wied.presentation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.wied.domain.models.auth.AuthResult
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.navigation.global.AuthNavGraph
import ua.wied.presentation.common.navigation.GlobalNav
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.utils.ToastManager

@Composable
fun AuthScreen(
    globalNavController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        ToastManager.processToastMessages(context)
    }

    LaunchedEffect(viewModel) {
        viewModel.authResult.collect { result ->
            if (result is AuthResult.Success) {
                globalNavController.navigate(GlobalNav.Main(result.isManager)) {
                    popUpTo(GlobalNav.Auth) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else if (result is AuthResult.Error) {
                viewModel.showErrorToast(result.errorMessage)
            }
        }
    }

    Column (modifier = Modifier.fillMaxSize().padding(dimen.containerPadding)) {
        AuthNavGraph(authViewModel = viewModel)
    }


    if (viewModel.state.isLoading) {
        LoadingIndicator()
    }
}
