package ua.wied.presentation.common.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.screens.main.reports.ReportsScreen

fun NavGraphBuilder.reportsNavGraph(navController: NavHostController) {
    composable(
        route = ReportNav.Reports.route
    ) {
        ReportsScreen(navController)
    }
}