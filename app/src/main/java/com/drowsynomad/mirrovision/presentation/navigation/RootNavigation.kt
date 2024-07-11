package com.drowsynomad.mirrovision.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.components.MainTestScreen
import com.drowsynomad.mirrovision.presentation.screens.introCategories.IntroCategoriesScreen
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.PresetHabitScreen
import com.drowsynomad.mirrovision.presentation.utils.fromJsonList
import com.drowsynomad.mirrovision.presentation.utils.toJson
import kotlin.reflect.KClass

/**
 * @author Roman Voloshyn (Created on 19.06.2024)
 */

typealias Navigation = () -> Unit

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<Routes.IntroCategoriesScreen> = Routes.IntroCategoriesScreen::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Routes.IntroCategoriesScreen> {
            IntroCategoriesScreen(
                viewModel = hiltViewModel(),
                onNavigateNext = {
                    navController.navigate(Routes.PresetHabitScreen(it.toJson()))
                }
            )
        }

        composable<Routes.MainTestScreen> {
            MainTestScreen()
        }

        composable<Routes.PresetHabitScreen> {
            val categories = it
                .toRoute<Routes.PresetHabitScreen>()
                .rawCategoryList
                .fromJsonList<Array<CategoryUI>>().toList()
            PresetHabitScreen(categories = categories, viewModel = hiltViewModel()) {
                navController.popBackStack()
            }
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