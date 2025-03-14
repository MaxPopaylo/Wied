package ua.wied.presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ua.wied.presentation.common.navigation.global.MainNavGraph
import ua.wied.presentation.screens.main.composables.MainBottomAppBar
import ua.wied.presentation.screens.main.composables.MainTopAppBar

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
        ) {
            MainNavGraph(navController)
        }
    }
}