package com.drowsynomad.mirrovision.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitNavigationModel
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.ChooseIconScreen
import com.drowsynomad.mirrovision.presentation.screens.dashboard.DashboardScreen
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CreateHabitScreen
import com.drowsynomad.mirrovision.presentation.screens.introCategories.IntroCategoriesScreen
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.PresetHabitScreen
import com.drowsynomad.mirrovision.presentation.screens.splashLoading.SplashLoadingScreen
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.composableOf
import com.drowsynomad.mirrovision.presentation.utils.fromJson
import com.drowsynomad.mirrovision.presentation.utils.toJson
import kotlin.reflect.KClass

/**
 * @author Roman Voloshyn (Created on 19.06.2024)
 */

typealias Navigation = () -> Unit

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: KClass<Routes.SplashLoadingScreen> = Routes.SplashLoadingScreen::class
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
       composable<Routes.SplashLoadingScreen> {
           SplashLoadingScreen(
               viewModel = hiltViewModel(),
               onNewUserNavigation = navController::navigateToIntro,
               onExistedUserNavigation = navController::navigateToDashboard
           )
       }

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
                navBackStackEntry.savedStateHandle
                    .get<HabitUI?>(Routes.CreateHabitScreen.parameterKey)

            createdHabit?.let { habit ->
                if(!createdHabits.contains(habit))
                    createdHabits.add(habit)
            }

            val categories = route
                .rawCategoryList.string
                .fromJson<Array<CategoryUI>>().toList()
                .map { category ->
                    val attachedHabits =
                        createdHabits.lastOrNull { habit -> habit.attachedCategoryId == category.id }
                    if(attachedHabits != null)
                        category.copy(habits = mutableStateListOf(attachedHabits))
                    else category
                }
            PresetHabitScreen(
                categories = categories,
                viewModel = hiltViewModel(),
                onCreateHabit = navController::navigateToHabitCreating,
                onBackNavigation = navController::popBackStack,
                onNextNavigation = {
                    createdHabits.clear()
                    navController.navigateToHomeFromPreset()
                }
            )
        }

        composableOf<Routes.ChooseIconScreen, CategoryMainColor> { args, _ ->
            ChooseIconScreen(
                viewModel = hiltViewModel(),
                categoryMainColor = args.color,
                onBackNavigation = navController::popBackStack,
                onIconSelected = { iconRes ->
                    navController.returnWithResult(iconRes, Routes.ChooseIconScreen.parameterKey)
                }
            )
        }

        composableOf<Routes.CreateHabitScreen, HabitNavigationModel> { route, navBackStackEntry ->
            val assets = route.categoryAssets

            val selectedIcon: Int? =
                navBackStackEntry.savedStateHandle[Routes.ChooseIconScreen.parameterKey]

            CreateHabitScreen(
                viewModel = hiltViewModel(),
                habitUI = assets.toHabitUI(selectedIcon),
                onChooseIconNavigation = navController::navigateToIconChooser,
                onBackNavigation = navController::popBackStack,
                onSaveHabit = navController::returnToHabitPresetWithCreatedHabit
            )
        }

        composableOf<Routes.HomeScreen, EmptyParameters> { _, _ ->
            DashboardScreen(viewModel = hiltViewModel())
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