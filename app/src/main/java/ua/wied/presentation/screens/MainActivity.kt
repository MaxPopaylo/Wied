package ua.wied.presentation.screens

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.wied.presentation.common.navigation.GlobalNav
import ua.wied.presentation.common.navigation.GlobalNavGraph
import ua.wied.presentation.common.theme.WiEDTheme
import kotlin.getValue
import ua.wied.presentation.common.utils.MyContextWrapper

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
            val systemDarkTheme = isSystemInDarkTheme()

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                val activity = this
                val previousLanguage = remember { mutableStateOf(settings.language) }
                LaunchedEffect(settings.language) {
                    if (settings.language != previousLanguage.value) {
                        previousLanguage.value = settings.language
                        activity.recreate()
                    }
                }
            }

            LaunchedEffect(settings.darkTheme) {
                if (settings.darkTheme == null) {
                    viewModel.setDarkTheme(systemDarkTheme)
                }
            }

            WiEDTheme(settings) {
                val navController: NavHostController = rememberNavController()
                GlobalNavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            if (newBase != null) MyContextWrapper.wrap(newBase) else newBase
        )
    }
}