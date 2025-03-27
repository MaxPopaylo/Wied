package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.navigation.InstructionType
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.navigation.ReportType
import ua.wied.presentation.common.navigation.ReportsType
import ua.wied.presentation.screens.main.reports.ReportsScreen
import ua.wied.presentation.screens.main.reports.by_status.ReportsByStatusScreen
import ua.wied.presentation.screens.main.reports.create.CreateReportScreen
import ua.wied.presentation.screens.main.reports.detail.ReportDetailScreen
import ua.wied.presentation.screens.main.reports.status_list.ReportStatusListScreen
import kotlin.reflect.typeOf

fun NavGraphBuilder.reportsNavGraph(navController: NavHostController) {
    composable<ReportNav.Reports>(
        enterTransition = {
            fadeIn(tween(500))
        },
        exitTransition = {
            fadeOut(tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
        },
        popExitTransition = {
            fadeOut(tween(300))
        }
    ) {
        ReportsScreen(navController)
    }

    composable<ReportNav.ReportStatusList>(
        typeMap = mapOf(
            typeOf<Instruction>() to InstructionType
        ),
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
        },
        popExitTransition = {
            fadeOut(tween(300))
        }
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.ReportStatusList>()
        ReportStatusListScreen(
            navController = navController,
            instruction = args.instruction
        )
    }

    composable<ReportNav.ReportsByStatusList>(
        typeMap = mapOf(
            typeOf<List<Report>>() to ReportsType,
            typeOf<Instruction>() to InstructionType
        ),
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
        },
        popExitTransition = {
            fadeOut(tween(300))
        }
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.ReportsByStatusList>()
        ReportsByStatusScreen(
            navController = navController,
            reports = args.reports,
            instruction = args.instruction
        )
    }

    composable<ReportNav.ReportDetail>(
        typeMap = mapOf(
            typeOf<Report>() to ReportType
        ),
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
        },
        popExitTransition = {
            fadeOut(tween(300))
        }
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.ReportDetail>()
        ReportDetailScreen(
            report = args.report
        )
    }

    composable<ReportNav.CreateReport>(
        typeMap = mapOf(
            typeOf<Instruction>() to InstructionType
        ),
        enterTransition = {
            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
        },
        exitTransition = {
            fadeOut(tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
        },
        popExitTransition = {
            fadeOut(tween(300))
        }
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.CreateReport>()
        CreateReportScreen(
            instruction = args.instruction
        )
    }
}