package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.navigation.AuthNav
import ua.wied.presentation.common.navigation.ElementType
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.InstructionType
import ua.wied.presentation.common.navigation.PeopleNav
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.UserType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.people.PeopleScreen
import ua.wied.presentation.screens.people.PeopleViewModel
import ua.wied.presentation.screens.people.create.CreateEmployeeScreen
import ua.wied.presentation.screens.people.create.CreateEmployeeViewModel
import ua.wied.presentation.screens.people.create.model.CreateEmployeeState
import ua.wied.presentation.screens.people.detail.EmployeeDetail
import ua.wied.presentation.screens.people.model.PeopleState
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.peopleNavGraph(
    navController: NavHostController,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit
) {
    screenComposable<
        PeopleNav.People,
        PeopleViewModel,
        PeopleState
    >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, backStakeEntry ->
        PeopleScreen (
            state = state,
            isManager = isManager,
            savedStateHandle = backStakeEntry.savedStateHandle,
            onEvent = vm::onEvent,
            onMainEvent = onMainEvent,
            navigateToDetail = { user ->
                navController.navigate(PeopleNav.EmployeeDetail(user))
            },
            navigateToCreation = {
                navController.navigate(PeopleNav.CreateEmployee)
            }
        )
    }

    screenComposable<PeopleNav.EmployeeDetail>(
        TabType.BACK,
        typeMap = mapOf(
            typeOf<User>() to UserType
        ),
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<PeopleNav.EmployeeDetail>()
        EmployeeDetail(args.user)
    }

    screenComposable<
        PeopleNav.CreateEmployee,
        CreateEmployeeViewModel,
        CreateEmployeeState
    >(
        tabType = TabType.BACK,
        stateProvider = { it.uiState }
    ) { vm, state, _ ->
        CreateEmployeeScreen (
            state = state,
            onEvent = vm::onEvent,
            backToPeopleScreen = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("shouldRefresh", it)

                navController.popBackStack()
            }
        )
    }
}