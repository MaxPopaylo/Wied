package ua.wied.presentation.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.wied.domain.models.auth.AuthResult
import ua.wied.presentation.common.composable.ErrorAlertDialog
import ua.wied.presentation.common.composable.LoadingIndicator
import ua.wied.presentation.common.navigation.AuthNavGraph
import ua.wied.presentation.common.navigation.Global

@Composable
fun AuthScreen(
    globalNavController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    LaunchedEffect(viewModel) {
        viewModel.authResult.collect { result ->
            if (result is AuthResult.Success) {
                globalNavController.navigate(Global.Main.route) {
                    popUpTo(Global.Auth.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            } else if (result is AuthResult.Error) {
                viewModel.showErrorDialog(result.errorMessage)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AuthNavGraph(authViewModel = viewModel)
    }

    if (viewModel.pageState.showErrorDialog) {
        ErrorAlertDialog(
            text = viewModel.pageState.errorDialogMessage,
            title = "Authentication Failed",
            onDismiss = {
                viewModel.clearAll()
                viewModel.clearErrorDialog()
            }
        )
    }

    if (viewModel.pageState.isLoading) {
        LoadingIndicator()
    }
}