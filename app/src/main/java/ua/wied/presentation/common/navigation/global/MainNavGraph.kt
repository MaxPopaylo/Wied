package ua.wied.presentation.common.navigation.global

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.main.instructionNavGraph
import ua.wied.presentation.common.navigation.main.reportsNavGraph
import ua.wied.presentation.screens.main.MainViewModel
import ua.wied.presentation.screens.main.models.MainState

@Composable
fun MainNavGraph(
    navController: NavHostController,
    mainState: MainState
) {
    NavHost(
        navController = navController,
        startDestination = InstructionNav.Instructions
    ) {

        instructionNavGraph(navController, mainState)
        reportsNavGraph(navController)

    }
}
