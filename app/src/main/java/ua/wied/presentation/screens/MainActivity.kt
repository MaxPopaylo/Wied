package ua.wied.presentation.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.wied.presentation.common.navigation.GlobalNav
import ua.wied.presentation.common.navigation.GlobalNavGraph
import ua.wied.presentation.common.theme.WiEDTheme
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isManager = intent.getBooleanExtra("isManager", false)

        val startDestination = intent.getStringExtra("startDestination")?.let {
            when (it) {
                GlobalNav.Auth::class.simpleName -> GlobalNav.Auth
                GlobalNav.Main::class.simpleName -> GlobalNav.Main(isManager)
                else -> GlobalNav.Auth
            }
        } ?: GlobalNav.Auth

        setContent {
            val settings by viewModel.settings.collectAsState()

            WiEDTheme(settings) {
                val navController: NavHostController = rememberNavController()
                GlobalNavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}
