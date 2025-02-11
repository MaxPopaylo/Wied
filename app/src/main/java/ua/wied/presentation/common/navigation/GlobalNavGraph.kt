package ua.wied.presentation.common.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.wied.presentation.screens.auth.AuthScreen


@Composable
fun GlobalNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Global.Auth.route,
            enterTransition = {
                return@composable fadeIn(tween(500))
            },
            exitTransition = {
                return@composable fadeOut(tween(700))
            }
        ) {
            AuthScreen(globalNavController = navController)
        }

        composable(
            route = Global.Main.route
        ) {

        }

    }
}

