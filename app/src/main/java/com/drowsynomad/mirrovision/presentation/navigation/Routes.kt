package com.drowsynomad.mirrovision.presentation.navigation

import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 21.06.2024)
 */

@Serializable
sealed class Routes {
    val name = this.toString().substringAfterLast(".")

    @Serializable
    data object MainTestScreen: Routes()
    @Serializable
    data object SecondaryTestScreen: Routes()

    @Serializable
    data object SplashScreen: Routes()

    @Serializable
    data object IntroCategoriesScreen: Routes()
    @Serializable
    data class PresetHabitScreen(
        val rawCategoryList: String = emptyString()
    ): Routes()

    @Serializable
    data class CreateHabitScreen(
        val categoryAssets: CategoryAssets
    ): Routes()

    @Serializable
    data object TimerScreen: Routes()
    @Serializable
    data object NotesScreen: Routes()
    @Serializable
    data object HomeScreen: Routes()
    @Serializable
    data object GuideScreen: Routes()
    @Serializable
    data object StatisticScreen: Routes()
}