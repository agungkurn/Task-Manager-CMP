package org.example.compose.ui

import android.view.Window
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun AdjustSystemBar(isDark: Boolean) {
    val view = LocalView.current

    if (!view.isInEditMode) { // Avoid running in preview mode
        SideEffect {
            val window: Window = (view.context as ComponentActivity).window

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDark
            }
        }
    }
}