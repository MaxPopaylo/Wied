package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.screens.main.instructions.InstructionsScreen

fun NavGraphBuilder.instructionNavGraph(navController: NavHostController) {
    composable(
        route = InstructionNav.Instructions.route,
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
        InstructionsScreen(navController)
    }
}