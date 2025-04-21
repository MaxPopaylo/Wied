package ua.wied.presentation.common.navigation.global

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import ua.wied.presentation.common.navigation.AuthNav
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.auth.AuthViewModel
import ua.wied.presentation.screens.auth.SignInScreen
import ua.wied.presentation.screens.auth.SignUpScreen

@Composable
fun AuthNavGraph(
    authViewModel: AuthViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AuthNav.SignIn
    ) {
        screenComposable<AuthNav.SignIn>(TabType.GLOBAL) {
            SignInScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        screenComposable<AuthNav.SignUp>(TabType.GLOBAL) {
            SignUpScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }
    }
}
