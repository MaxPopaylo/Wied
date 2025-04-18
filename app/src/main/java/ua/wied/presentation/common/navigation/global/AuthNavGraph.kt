package ua.wied.presentation.common.navigation.global

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.wied.presentation.common.navigation.AuthNav
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

        composable<AuthNav.SignIn>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
            }
        ) {
            SignInScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable<AuthNav.SignUp>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
            }
        ) {
            SignUpScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

    }
}
