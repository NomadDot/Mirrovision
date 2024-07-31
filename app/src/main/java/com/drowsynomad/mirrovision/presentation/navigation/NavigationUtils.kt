package com.drowsynomad.mirrovision.presentation.navigation

import androidx.navigation.NavController
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

fun NavController.navigateToHabitCreating(
    args: HabitUI,
    isForIntro: Boolean = false
) {
    this.navigate(Routes.CreateHabitScreen(args.toHabitNavigation(isForIntro)))
}

fun NavController.navigateToDashboardFromPreset() {
    this.navigate(Routes.DashboardScreen) {
        popUpTo<Routes.DashboardScreen> { inclusive = true }
    }
}

fun NavController.navigateToIntro() {
    this.navigate(Routes.IntroCategoriesScreen) {
        popUpTo<Routes.IntroCategoriesScreen> { inclusive = true }
    }
}

fun NavController.navigateToDashboard() {
    this.navigate(Routes.DashboardScreen) {
        popUpTo<Routes.SplashLoadingScreen> { inclusive = true }
    }
}

fun NavController.returnToHabitPresetWithCreatedHabit(habit: HabitUI) {
    this.previousBackStackEntry?.savedStateHandle?.set(Routes.CreateHabitScreen.parameterKey, habit)
    this.popBackStack()
}

fun <R> NavController.returnWithResult(result: R, key: String) {
    this.previousBackStackEntry?.savedStateHandle?.set(key, result)
    this.popBackStack()
}

fun NavController.navigateToIconChooser(color: CategoryMainColor) {
    this.navigate(Routes.ChooseIconScreen(color))
}