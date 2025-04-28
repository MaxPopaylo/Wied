package ua.wied.presentation.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.global.MainNavGraph
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.main.composable.MainBottomAppBar
import ua.wied.presentation.screens.main.composable.MainTopAppBar
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainScreen(
    isManager: Boolean,
    navController: NavHostController = rememberNavController(),
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentDestinationRoute) {
        when {
            currentDestinationRoute == InstructionNav.Instructions::class.qualifiedName -> {}
            currentDestinationRoute?.startsWith(InstructionNav.InstructionDetail::class.qualifiedName ?: "") == true -> {}
            else -> {
                onEvent(MainEvent.FabVisibilityChanged(false))
                onEvent(MainEvent.FabClickChanged(value = {}))
            }
        }
    }
    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.isFabVisible,
                enter = fadeIn() + scaleIn(),
                exit  = fadeOut() + scaleOut(),
            ) {
                FloatingActionButton(
                    containerColor = colors.tintColor,
                    contentColor = Color.White,
                    shape = dimen.shape,
                    onClick = state.fabClick,
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add item")
                }
            }
        },
        topBar = {
            MainTopAppBar(
                isManager = isManager,
                navController = navController,
                onEvent =  onEvent,
                mainState = state
            )
        },
        bottomBar = {
            MainBottomAppBar(isManager, navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colors.primaryBackground)
                .padding(horizontal = dimen.containerPadding)
        ) {
            MainNavGraph(navController, state, onEvent)
        }
    }
}