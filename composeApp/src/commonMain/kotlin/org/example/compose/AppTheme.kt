package org.example.compose

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import mycompose.composeapp.generated.resources.Res
import mycompose.composeapp.generated.resources.theme_auto
import mycompose.composeapp.generated.resources.theme_dark
import mycompose.composeapp.generated.resources.theme_light
import org.jetbrains.compose.resources.StringResource

sealed class AppTheme(
    val id: Int,
    val stringResource: StringResource,
    val colorScheme: ColorScheme?
) {
    data object System :
        AppTheme(id = 0, stringResource = Res.string.theme_auto, colorScheme = null)

    data object Dark :
        AppTheme(id = 1, stringResource = Res.string.theme_dark, colorScheme = darkColorScheme())

    data object Light :
        AppTheme(id = 2, stringResource = Res.string.theme_light, colorScheme = lightColorScheme())
}