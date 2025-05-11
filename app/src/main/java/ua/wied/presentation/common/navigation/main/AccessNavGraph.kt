package ua.wied.presentation.common.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.navigation.AccessNav
import ua.wied.presentation.common.navigation.FolderType
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.accesses.AccessesScreen
import ua.wied.presentation.screens.accesses.AccessesViewModel
import ua.wied.presentation.screens.accesses.detail.AccessDetailScreen
import ua.wied.presentation.screens.accesses.detail.AccessDetailViewModel
import ua.wied.presentation.screens.accesses.detail.model.AccessDetailState
import ua.wied.presentation.screens.accesses.model.AccessesState
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState
import kotlin.reflect.typeOf


fun NavGraphBuilder.accessNavGraph(
    navController: NavHostController,
    mainState: MainState,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit
) {
    screenComposable<
        AccessNav.Accesses,
        AccessesViewModel,
        AccessesState
     >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, backStakeEntry ->
        AccessesScreen (
            state = state,
            isManager = isManager,
            savedStateHandle = backStakeEntry.savedStateHandle,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            navigateToDetail = { folder ->
                navController.navigate(AccessNav.FolderDetail(folder))
            },
            navigateToCreation = {
                navController.navigate(AccessNav.CreateFolder)
            }
        )
    }

    screenComposable<
        AccessNav.FolderDetail,
        AccessDetailViewModel,
        AccessDetailState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Folder<Instruction>>() to FolderType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<AccessNav.FolderDetail>()
        AccessDetailScreen (
            state = state,
            isManager = isManager,
            folder = args.folder,
            isEditing = mainState.isInstructionEditing,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            backToInstructions = {
                navController.popBackStack()
            }
        )
    }
}