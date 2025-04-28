package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.navigation.ElementType
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.InstructionType
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.instructions.InstructionViewModel
import ua.wied.presentation.screens.instructions.InstructionsScreen
import ua.wied.presentation.screens.instructions.create.CreateInstructionScreen
import ua.wied.presentation.screens.instructions.create.CreateInstructionViewModel
import ua.wied.presentation.screens.instructions.create.models.CreateInstructionState
import ua.wied.presentation.screens.instructions.detail.InstructionDetailScreen
import ua.wied.presentation.screens.instructions.detail.InstructionDetailViewModel
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailState
import ua.wied.presentation.screens.instructions.elements.ElementDetailScreen
import ua.wied.presentation.screens.instructions.elements.ElementDetailViewModel
import ua.wied.presentation.screens.instructions.elements.create.CreateElementScreen
import ua.wied.presentation.screens.instructions.elements.create.CreateElementViewModel
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementState
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailState
import ua.wied.presentation.screens.instructions.model.InstructionsState
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.instructionNavGraph(
    navController: NavHostController,
    mainState: MainState,
    onMainEvent: (MainEvent) -> Unit
) {
    var elementCreateCallback = {}


    screenComposable<
        InstructionNav.Instructions,
        InstructionViewModel,
        InstructionsState
     >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, _ ->
        InstructionsScreen(
            state = state,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            navigateToDetail = { instruction ->
                navController.navigate(InstructionNav.InstructionDetail(instruction))
            },
            navigateToCreation = { orderNum, folderId ->
                navController.navigate(InstructionNav.CreateInstruction(orderNum, folderId))
            }
        )
    }

    screenComposable<
        InstructionNav.CreateInstruction,
        CreateInstructionViewModel,
        CreateInstructionState
     >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<InstructionNav.CreateInstruction>()
        CreateInstructionScreen(
            orderNum = args.orderNum,
            folderId = args.folderId,
            state = state,
            onEvent = vm::onEvent,
            backToInstructions = {
                navController.popBackStack()
            }
        )
    }


    screenComposable<
        InstructionNav.InstructionDetail,
        InstructionDetailViewModel,
        InstructionDetailState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Instruction>() to InstructionType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<InstructionNav.InstructionDetail>()
        InstructionDetailScreen(
            state = state,
            instruction = args.instruction,
            isEditing = mainState.isInstructionEditing,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            navigateToElementDetail = {
                navController.navigate(InstructionNav.InstructionElementDetail(it))
            },
            navigateToCreation = { orderNum, instructionId ->
                navController.navigate(InstructionNav.CreateElement(orderNum, instructionId))
            },
            backToInstructions = {
                navController.popBackStack()
            }
        )
    }

    screenComposable<
        InstructionNav.CreateElement,
        CreateElementViewModel,
        CreateElementState
    >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<InstructionNav.CreateElement>()
        CreateElementScreen (
            orderNum = args.orderNum,
            instructionId = args.instructionId,
            state = state,
            onEvent = vm::onEvent,
            backToInstructionDetail = {
                elementCreateCallback()
                navController.popBackStack()
            }
        )
    }

    screenComposable<
         InstructionNav.InstructionElementDetail,
         ElementDetailViewModel,
         ElementDetailState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Element>() to ElementType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<InstructionNav.InstructionElementDetail>()
        ElementDetailScreen(
            state = state,
            element = args.element,
            isEditing = mainState.isInstructionEditing,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent
        )
    }
}