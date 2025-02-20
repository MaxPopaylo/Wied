package ua.wied.presentation.screens.splash

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.wied.R
import ua.wied.presentation.MainActivity
import ua.wied.presentation.common.navigation.Global

@SuppressLint("RestrictedApi", "CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(300)
            val startDestination = Global.Auth
            openActivity(MainActivity::class.java, startDestination.route)
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