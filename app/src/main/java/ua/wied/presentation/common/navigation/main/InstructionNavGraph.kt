package ua.wied.presentation.common.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.screens.main.instructions.InstructionsScreen

fun NavGraphBuilder.instructionNavGraph(navController: NavHostController) {
    composable(
        route = InstructionNav.Instructions.route
    ) {
        InstructionsScreen(navController)
    }
}