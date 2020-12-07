package dev.all4.algorithmSteps.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dev.all4.algorithmSteps.ui.theme.AlgorithmStepsTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgorithmStepsTheme {
                AppNavigation()
            }
        }
    }
}