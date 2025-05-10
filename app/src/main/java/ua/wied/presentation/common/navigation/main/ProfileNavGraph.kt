package ua.wied.presentation.common.navigation.main

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.navigation.NavGraphBuilder
import ua.wied.presentation.common.navigation.ProfileNav
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.profile.ProfileScreen
import ua.wied.presentation.screens.profile.ProfileViewModel
import ua.wied.presentation.screens.profile.model.ProfileState

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.profileNavGraph() {
    screenComposable<
            ProfileNav.Profile,
            ProfileViewModel,
            ProfileState
            >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, backStakeEntry ->
        ProfileScreen (
            state = state,
            onEvent = vm::onEvent
        )
    }
}