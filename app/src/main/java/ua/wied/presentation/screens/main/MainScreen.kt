package ua.wied.presentation.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ua.wied.presentation.common.navigation.global.MainNavGraph
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.main.composable.MainBottomAppBar
import ua.wied.presentation.screens.main.composable.MainTopAppBar

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            MainTopAppBar(navController)
        },
        bottomBar = {
            MainBottomAppBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(colors.primaryBackground)
                .padding(horizontal = dimen.containerPadding)
        ) {
            MainNavGraph(navController)
        }
    }
}