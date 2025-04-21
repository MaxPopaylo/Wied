package ua.wied.presentation.common.navigation.main

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
import ua.wied.presentation.screens.instructions.detail.InstructionDetailScreen
import ua.wied.presentation.screens.instructions.detail.InstructionDetailViewModel
import ua.wied.presentation.screens.instructions.detail.model.InstructionDetailState
import ua.wied.presentation.screens.instructions.elements.ElementDetailScreen
import ua.wied.presentation.screens.instructions.elements.ElementDetailViewModel
import ua.wied.presentation.screens.instructions.elements.model.ElementDetailState
import ua.wied.presentation.screens.instructions.model.InstructionsState
import ua.wied.presentation.screens.main.models.MainState
import kotlin.reflect.typeOf

fun NavGraphBuilder.instructionNavGraph(
    navController: NavHostController,
    mainState: MainState
) {
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
            navigateToDetail = {
                navController.navigate(InstructionNav.InstructionDetail(it))
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
            navigateToElementDetail = {
                navController.navigate(InstructionNav.InstructionElementDetail(it))
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
            onEvent = vm::onEvent
        )
    }
}