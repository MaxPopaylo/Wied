package ua.wied.presentation.common.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.wied.presentation.screens.auth.AuthScreen
import ua.wied.presentation.screens.main.MainScreen


@Composable
fun GlobalNavGraph(
    navController: NavHostController,
    startDestination: GlobalNav
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<GlobalNav.Auth>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
            }
        ) {
            AuthScreen(globalNavController = navController)
        }

        composable<GlobalNav.Main>(
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
            }
        ) {
            MainScreen()
        }

    }
}

