package ua.wied.presentation.screens.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ComponentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ua.wied.presentation.MainActivity

@SuppressLint("RestrictedApi", "CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(300)
            openActivity(MainActivity::class.java)
        }
    }

    private fun openActivity(activity: Class<*>){
        startActivity(Intent(this, activity))
        finish()
    }
}