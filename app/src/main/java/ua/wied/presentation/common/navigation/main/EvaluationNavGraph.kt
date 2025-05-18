package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ua.wied.presentation.common.navigation.EvaluationNav
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.evaluation.EvaluationsScreen
import ua.wied.presentation.screens.evaluation.EvaluationsViewModel
import ua.wied.presentation.screens.evaluation.model.EvaluationsState

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.evaluationNavGraph(
    navController: NavHostController,
) {
    screenComposable<
        EvaluationNav.Evaluations,
        EvaluationsViewModel,
        EvaluationsState
    >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, backStakeEntry ->
        EvaluationsScreen (
            state = state,
            onEvent = vm::onEvent,
            navigateToEvaluationList = {

            }
        )
    }
}