package ua.wied.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.wied.presentation.common.navigation.Global
import ua.wied.presentation.common.navigation.GlobalNavGraph
import ua.wied.presentation.common.theme.WiEDTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startDestination = intent.getStringExtra("startDestination") ?: Global.Auth.route

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
