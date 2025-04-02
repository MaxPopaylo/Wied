package ua.wied.presentation.screens.splash

import androidx.activity.ComponentActivity
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ua.wied.R
import ua.wied.presentation.screens.MainActivity
import ua.wied.presentation.common.navigation.GlobalNav

@SuppressLint("RestrictedApi", "CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.isUserAuthorized.collect { status ->
                val startDestination = if (status) GlobalNav.Main::class.simpleName!!
                else GlobalNav.Auth::class.simpleName!!

                openActivity(MainActivity::class.java, startDestination)
            }
        }
    }

    private fun openActivity(activity: Class<*>, startDestination: String) {
        val intent = Intent(this, activity).apply {
            putExtra("startDestination", startDestination)
        }
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.fade_in,
            R.anim.fade_out
        ).toBundle()

        startActivity(intent, options)
        finish()
    }
}