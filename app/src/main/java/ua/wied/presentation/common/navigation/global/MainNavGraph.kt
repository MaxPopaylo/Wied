package ua.wied.presentation.common.navigation.global

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.main.instructionNavGraph

@Composable
fun MainNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = InstructionNav.Instructions.route
    ) {

        instructionNavGraph(navController)



    }
}
