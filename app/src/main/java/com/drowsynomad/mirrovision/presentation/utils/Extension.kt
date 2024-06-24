package com.drowsynomad.mirrovision.presentation.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute

/**
 * @author Roman Voloshyn (Created on 21.06.2024)
 */

inline fun <reified T : Any> NavGraphBuilder.composableOf(
    noinline content: @Composable AnimatedContentScope.(T, NavBackStackEntry) -> Unit
) {
    composable<T> {
        val args = it.toRoute<T>()
        content(args, it)
    }
}