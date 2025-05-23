package org.example.compose.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarStyle

@Composable
actual fun AdjustSystemBar(isDark: Boolean) {
    LaunchedEffect(isDark) {
        val statusBarStyle = if (isDark) {
            UIStatusBarStyleLightContent
        } else {
            UIStatusBarStyleDarkContent
        }

        UIApplication.sharedApplication.setStatusBarStyle(statusBarStyle, animated = true)
    }
}