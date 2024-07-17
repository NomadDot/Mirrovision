package com.drowsynomad.mirrovision.presentation.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.MainTestScreen
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CreateHabitScreen
import com.drowsynomad.mirrovision.presentation.screens.introCategories.IntroCategoriesScreen
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.PresetHabitScreen
import com.drowsynomad.mirrovision.presentation.utils.composableOf
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
                     navController.navigate(Routes.PresetHabitScreen(StringParcel(it.toJson())))
                }
            )
        }

        composableOf<Routes.PresetHabitScreen, StringParcel> { route, navBackStackEntry ->
            val createdHabits = rememberSaveable {
                mutableListOf<HabitUI>()
            }
            val createdHabit =
                navBackStackEntry.savedStateHandle.get<HabitUI?>("createdHabit")

            createdHabit?.let { habit ->
                if(!createdHabits.contains(habit))
                    createdHabits.add(habit)
            }

            val categories = route
                .rawCategoryList.string
                .fromJson<Array<CategoryUI>>().toList()
                .map { category ->
                    val attachedHabits = createdHabits.filter { habit -> habit.attachedCategoryId == category.id }
                    if(attachedHabits.isNotEmpty())
                        category.copy(habits = attachedHabits)
                    else category
                }
            PresetHabitScreen(
                categories = categories,
                viewModel = hiltViewModel(),
                onCreateHabit = navController::navigateToHabitCreating,
                onBackNavigation = navController::popBackStack
            )
        }

        composableOf<Routes.CreateHabitScreen, CategoryAssets> { route, navBackStackEntry ->
            val assets = route.categoryAssets
            CreateHabitScreen(
                viewModel = hiltViewModel(),
                HabitUI(attachedCategoryId = assets.categoryId, backgroundColor =  assets.color),
                onBackNavigation = navController::popBackStack,
                onSaveHabit = navController::returnToHabitPresetWithCreatedHabit
            )
        }
    }
}

fun NavController.navigateToHabitCreating(categoryAssets: CategoryAssets) {
    this.navigate(Routes.CreateHabitScreen(categoryAssets))
}

fun NavController.returnToHabitPresetWithCreatedHabit(habit: HabitUI) {
    this.previousBackStackEntry?.savedStateHandle?.set("createdHabit", habit)
    this.popBackStack()
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