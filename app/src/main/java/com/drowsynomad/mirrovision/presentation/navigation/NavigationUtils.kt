package com.drowsynomad.mirrovision.presentation.navigation

import androidx.navigation.NavController
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitNavigationModel
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

fun NavController.navigateToHabitCreating(categoryAssets: HabitNavigationModel) {
    this.navigate(Routes.CreateHabitScreen(categoryAssets))
}

fun NavController.navigateToHomeFromPreset() {
    this.navigate(Routes.HomeScreen) {
        popUpTo<Routes.HomeScreen> { inclusive = true }
    }
}

fun NavController.navigateToIntro() {
    this.navigate(Routes.IntroCategoriesScreen) {
        popUpTo<Routes.IntroCategoriesScreen> { inclusive = true }
    }
}

fun NavController.navigateToDashboard() {
    this.navigate(Routes.HomeScreen) {
        popUpTo<Routes.SplashLoadingScreen> { inclusive = true }
    }
}

fun NavController.returnToHabitPresetWithCreatedHabit(habit: HabitUI) {
    this.previousBackStackEntry?.savedStateHandle?.set(Routes.CreateHabitScreen.parameterKey, habit)
    this.popBackStack()
}