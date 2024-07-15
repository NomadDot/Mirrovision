package com.drowsynomad.mirrovision.presentation.navigation

import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.components.MainTestScreen
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CreateHabitScreen
import com.drowsynomad.mirrovision.presentation.screens.introCategories.IntroCategoriesScreen
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.PresetHabitScreen
import com.drowsynomad.mirrovision.presentation.utils.fromJson
import com.drowsynomad.mirrovision.presentation.utils.toJson
import kotlin.reflect.KClass
import kotlin.reflect.typeOf

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

        composable<Routes.PresetHabitScreen>(
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left, tween(300))
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
            },
            popExitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right, tween(300))
            }
        ) {
            val categories = it
                .toRoute<Routes.PresetHabitScreen>()
                .rawCategoryList
                .fromJson<Array<CategoryUI>>().toList()
            PresetHabitScreen(
                categories = categories,
                viewModel = hiltViewModel(),
                onCreateHabit = { categoryUI ->
                    navController.navigate(Routes.CreateHabitScreen(categoryUI))
                }
            ) {
                navController.popBackStack()
            }
        }

        composable<Routes.CreateHabitScreen>(
            typeMap = getTypedMap<CategoryAssets>()
        ) {
            CreateHabitScreen(
                viewModel = hiltViewModel(),
                it.toRoute<Routes.CreateHabitScreen>().categoryAssets,
                onBackNavigation = {
                    navController.popBackStack()
                }
            ) {

            }
        }

        composable<Routes.MainTestScreen> {
            MainTestScreen()
        }
    }
}

private inline fun <reified T: Parcelable> getTypedMap() = mapOf(typeOf<T>() to NavTypeFactory.create<T>())

private class NavTypeFactory {
    companion object {
        inline fun <reified T: Parcelable> create(): NavType<T> {
            return object : NavType<T>(false) {
                override fun get(bundle: Bundle, key: String): T? =
                    @Suppress("DEPRECATION") // for backwards compatibility
                    bundle.getParcelable(key)

                override fun put(bundle: Bundle, key: String, value: T) =
                    bundle.putParcelable(key, value)

                override fun parseValue(value: String): T = value.fromJson()

                override fun serializeAsValue(value: T): String = value.toJson()

                override val name: String = "ScreenInfo"
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