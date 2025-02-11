package ua.wied.presentation.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ua.wied.domain.models.auth.AuthResult
import ua.wied.presentation.common.composable.ErrorAlertDialog
import ua.wied.presentation.common.navigation.AuthNavGraph
import ua.wied.presentation.common.navigation.Global
import ua.wied.presentation.common.theme.WiEDTheme.colors

@Composable
fun AuthScreen(
    globalNavController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
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
        AuthNavGraph(
            authViewModel = viewModel,
            navController = navController
        )
//        SignUpScreen(viewModel,navController)
    }

    if (viewModel.pageState.showErrorDialog) {
        ErrorAlertDialog(
            text = viewModel.pageState.errorDialogMessage,
            title = "Authentication Failed"
        ) {
            viewModel.clearAll()
            viewModel.clearErrorDialog()
        }
    }

    if (viewModel.pageState.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0x80000000)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = colors.tintColor,
                strokeWidth = 5.dp
            )
        }
    }
}