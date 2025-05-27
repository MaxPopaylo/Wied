package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.navigation.EvaluationNav
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.UserType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.evaluation.create.CreateEvaluationScreen
import ua.wied.presentation.screens.evaluation.create.model.CreateEvaluationState
import ua.wied.presentation.screens.main.models.MainEvent
import kotlin.reflect.typeOf
import ua.wied.presentation.common.navigation.AiInstructionNav
import ua.wied.presentation.screens.ai_instruction.AiInstructionHistoryScreen
import ua.wied.presentation.screens.ai_instruction.AiInstructionHistoryViewModel
import ua.wied.presentation.screens.ai_instruction.create.CreateAiRequestScreen
import ua.wied.presentation.screens.ai_instruction.create.CreateAiRequestViewModel
import ua.wied.presentation.screens.ai_instruction.create.model.CreateAiRequestState
import ua.wied.presentation.screens.ai_instruction.model.AiInstructionHistoryState

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.aiInstructionNavGraph(
    navController: NavHostController,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit
) {
    screenComposable<
        AiInstructionNav.AiInstructionResponseHistory,
        AiInstructionHistoryViewModel,
        AiInstructionHistoryState
    >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        AiInstructionHistoryScreen (
            state = state,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            isManager = isManager,
            savedStateHandle = backStackEntry.savedStateHandle,
            navigateToCreation = {
                navController.navigate(
                    AiInstructionNav.CreateAiRequest
                )
            }
        )
    }

    screenComposable<
        AiInstructionNav.CreateAiRequest,
        CreateAiRequestViewModel,
        CreateAiRequestState
    >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        CreateAiRequestScreen (
            state = state,
            onEvent = vm::onEvent,
            backToInstructions = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldRefresh", it)

                navController.popBackStack()
            }
        )
    }
}