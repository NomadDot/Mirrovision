package com.drowsynomad.mirrovision.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = LightPrimary,
    secondary = LightPrimaryAccentColor,
    tertiary = LightMainHintColor,
    primaryContainer = Color.White,
    surfaceContainer = LightMainBackground
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightPrimaryAccentColor,
    tertiary = LightMainHintColor,
    primaryContainer = Color.White,
    surfaceContainer = LightMainBackground
)

@Composable
fun MirrovisionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MirrovisionTypography,
        content = content
    )
}