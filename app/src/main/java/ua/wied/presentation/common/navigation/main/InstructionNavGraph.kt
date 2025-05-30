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
import ua.wied.presentation.screens.instructions.access.InstructionAccessScreen
import ua.wied.presentation.screens.instructions.access.InstructionAccessViewModel
import ua.wied.presentation.screens.instructions.access.model.InstructionAccessState
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
import ua.wied.presentation.screens.instructions.video.VideoScreen
import ua.wied.presentation.screens.instructions.video.VideoViewModel
import ua.wied.presentation.screens.instructions.video.model.VideoState
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.instructionNavGraph(
    navController: NavHostController,
    mainState: MainState,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit
) {
    screenComposable<
        InstructionNav.Instructions,
        InstructionViewModel,
        InstructionsState
     >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, backStakeEntry ->
        InstructionsScreen(
            state = state,
            isManager = isManager,
            savedStateHandle = backStakeEntry.savedStateHandle,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            navigateToDetail = { instruction ->
                navController.navigate(InstructionNav.InstructionDetail(instruction))
            },
            navigateToVideoScreen = {
                navController.navigate(InstructionNav.Video(it))
            },
            navigateToCreation = { orderNum, folderId ->
                navController.navigate(InstructionNav.CreateInstruction(orderNum, folderId))
            },
            navigateToAccess = { instructionId ->
                navController.navigate(InstructionNav.InstructionAccess(instructionId))
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
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldRefresh", it)

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
            isManager = isManager,
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
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldRefresh", it)

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
            isEditing = mainState.isElementEditing,
            isDeleting = mainState.isElementDeleting,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            onPlayerEvent = vm::onEvent,
            backToInstruction = {
                navController.popBackStack()
            }
        )
    }

    screenComposable<
        InstructionNav.Video,
        VideoViewModel,
        VideoState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Instruction>() to InstructionType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<InstructionNav.Video>()
        VideoScreen (
            initInstruction = args.instruction,
            state = state,
            onEvent = vm::onEvent,
            onPlayerEvent = vm::onEvent,
            onBackToInstructions = {
                navController.navigate(InstructionNav.Instructions) {
                    launchSingleTop = true
                    restoreState = false
                    popUpTo(navController.graph.id) {
                        saveState = false
                    }
                }
            }
        )
    }

    screenComposable<
        InstructionNav.InstructionAccess,
        InstructionAccessViewModel,
        InstructionAccessState
    >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<InstructionNav.InstructionAccess>()
        InstructionAccessScreen(
            instructionId = args.instructionId,
            state = state,
            onEvent = vm::onEvent,
            isManager = isManager,
            onMainEvent = onMainEvent
        )
    }
}