package com.drowsynomad.mirrovision.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.components.MainTestScreen
import com.drowsynomad.mirrovision.presentation.utils.composableOf
import kotlin.reflect.KClass

/**
 * @author Roman Voloshyn (Created on 19.06.2024)
 */

typealias Navigation = () -> Unit

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<Routes.MainTestScreen> = Routes.MainTestScreen::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composableOf<Routes.MainTestScreen> { _, _ ->
            MainTestScreen()
        }
    }
}

sealed class MainBottomNavItem(
    val route: Routes, @DrawableRes val icon: Int
) {
    data object Timer : MainBottomNavItem(Routes.TimerScreen, R.drawable.ic_round_timer)
    data object Statistic : MainBottomNavItem(Routes.StatisticScreen, R.drawable.ic_statistic)
    data object Notes : MainBottomNavItem(Routes.NotesScreen, R.drawable.ic_notes)
    data object Guide : MainBottomNavItem(Routes.GuideScreen, R.drawable.ic_guide)
    data object Empty : MainBottomNavItem(Routes.GuideScreen, R.drawable.ic_guide)

    companion object {
        val values = listOf(Timer, Statistic, Empty, Notes, Guide)
    }
}