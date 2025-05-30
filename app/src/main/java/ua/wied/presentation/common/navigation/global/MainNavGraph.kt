package ua.wied.presentation.common.navigation.global

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.main.accessNavGraph
import ua.wied.presentation.common.navigation.main.aiInstructionNavGraph
import ua.wied.presentation.common.navigation.main.evaluationNavGraph
import ua.wied.presentation.common.navigation.main.instructionNavGraph
import ua.wied.presentation.common.navigation.main.peopleNavGraph
import ua.wied.presentation.common.navigation.main.profileNavGraph
import ua.wied.presentation.common.navigation.main.reportsNavGraph
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState

@Composable
fun MainNavGraph(
    navController: NavHostController,
    mainState: MainState,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = InstructionNav.Instructions,
    ) {
        instructionNavGraph(
            navController = navController,
            mainState = mainState,
            isManager = isManager,
            onMainEvent = onMainEvent
        )

        reportsNavGraph(navController)

        profileNavGraph()

        peopleNavGraph(
            navController = navController,
            isManager = isManager,
            onMainEvent = onMainEvent
        )

        accessNavGraph (
            navController = navController,
            mainState = mainState,
            isManager = isManager,
            onMainEvent = onMainEvent
        )

        evaluationNavGraph(
            navController = navController,
            isManager = isManager,
            onMainEvent = onMainEvent
        )

        aiInstructionNavGraph(
            navController = navController,
            isManager = isManager,
            onMainEvent = onMainEvent
        )
    }
}
