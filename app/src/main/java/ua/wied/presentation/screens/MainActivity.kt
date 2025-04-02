package ua.wied.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.wied.presentation.common.navigation.GlobalNav
import ua.wied.presentation.common.navigation.GlobalNavGraph
import ua.wied.presentation.common.theme.WiEDTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = intent.getStringExtra("startDestination")?.let {
            when (it) {
                GlobalNav.Auth::class.simpleName -> GlobalNav.Auth
                GlobalNav.Main::class.simpleName -> GlobalNav.Main
                else -> GlobalNav.Auth
            }
        } ?: GlobalNav.Auth

        setContent {
            WiEDTheme {
                val navController: NavHostController = rememberNavController()
                GlobalNavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}
