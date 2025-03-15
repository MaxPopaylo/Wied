package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.screens.main.reports.ReportsScreen

fun NavGraphBuilder.reportsNavGraph(navController: NavHostController) {
    composable(
        route = ReportNav.Reports.route,
        enterTransition = {
            fadeIn(tween(300))
        },
        exitTransition = {
            fadeOut(tween(300))
        },
        popEnterTransition = {
            fadeIn(tween(300))
        },
        popExitTransition = {
            fadeOut(tween(300))
        }
    ) {
        ReportsScreen(navController)
    }
}