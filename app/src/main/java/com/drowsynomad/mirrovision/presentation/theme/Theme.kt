package com.drowsynomad.mirrovision.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkPrimaryAccentColor,
    tertiary = LightMainHintColor,
    primaryContainer = Color.Black,
    surfaceContainer = DarkMainBackground,
    surfaceTint = DarkMainBackground
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightPrimaryAccentColor,
    tertiary = LightMainHintColor,
    primaryContainer = Color.White,
    surfaceContainer = LightMainBackground,
    surfaceTint = DarkMainBackground
)

@Composable
fun MirrovisionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    isSystemInDarkTheme()
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    updateColorPalette(darkTheme)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = MirrovisionTypography,
        content = content
    )
}