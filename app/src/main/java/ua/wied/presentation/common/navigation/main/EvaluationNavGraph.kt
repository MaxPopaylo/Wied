package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.navigation.EvaluationNav
import ua.wied.presentation.common.navigation.InstructionType
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.UserType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.evaluation.EvaluationsScreen
import ua.wied.presentation.screens.evaluation.EvaluationsViewModel
import ua.wied.presentation.screens.evaluation.create.CreateEvaluationScreen
import ua.wied.presentation.screens.evaluation.create.CreateEvaluationViewModel
import ua.wied.presentation.screens.evaluation.create.model.CreateEvaluationState
import ua.wied.presentation.screens.evaluation.employee_list.EmployeeEvaluationScreen
import ua.wied.presentation.screens.evaluation.employee_list.EmployeeEvaluationsViewModel
import ua.wied.presentation.screens.evaluation.employee_list.model.EmployeeEvaluationsState
import ua.wied.presentation.screens.evaluation.instruction_list.InstructionEvaluationScreen
import ua.wied.presentation.screens.evaluation.instruction_list.InstructionEvaluationsViewModel
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsState
import ua.wied.presentation.screens.evaluation.model.EvaluationsState
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState
import ua.wied.presentation.screens.people.create.CreateEmployeeViewModel
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.evaluationNavGraph(
    navController: NavHostController,
    mainState: MainState,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit
) {
    screenComposable<
        EvaluationNav.Evaluations,
        EvaluationsViewModel,
        EvaluationsState
    >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        EvaluationsScreen (
            state = state,
            onEvent = vm::onEvent,
            navigateToEvaluationList = { instruction ->
                navController.navigate(
                    EvaluationNav.InstructionEvaluations(
                        instruction = instruction
                    )
                )
            }
        )
    }

    screenComposable<
        EvaluationNav.InstructionEvaluations,
        InstructionEvaluationsViewModel,
        InstructionEvaluationsState
    >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<EvaluationNav.InstructionEvaluations>()
        InstructionEvaluationScreen(
            instruction = args.instruction,
            state = state,
            savedStateHandle = backStackEntry.savedStateHandle,
            onMainEvent = onMainEvent,
            isManager = isManager,
            onEvent = vm::onEvent,
            navigateToCreation = { instruction ->
                navController.navigate(EvaluationNav.CreateEvaluation(instruction = instruction))
            }
        )
    }

    screenComposable<
        EvaluationNav.EmployeeEvaluations,
        EmployeeEvaluationsViewModel,
        EmployeeEvaluationsState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<User>() to UserType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<EvaluationNav.EmployeeEvaluations>()
        EmployeeEvaluationScreen(
            employee = args.employee,
            state = state,
            isManager = isManager,
            savedStateHandle = backStackEntry.savedStateHandle,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            navigateToCreation = { user ->
                navController.navigate(EvaluationNav.CreateEvaluation(user))
            }
        )
    }

    screenComposable<
        EvaluationNav.CreateEvaluation,
        CreateEvaluationViewModel,
        CreateEvaluationState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<User>() to UserType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<EvaluationNav.CreateEvaluation>()
        CreateEvaluationScreen(
            employee = args.user,
            instruction = null,
            state = state,
            onEvent = vm::onEvent,
            backToEvaluations = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldRefresh", it)

                navController.popBackStack()
            }
        )
    }
}