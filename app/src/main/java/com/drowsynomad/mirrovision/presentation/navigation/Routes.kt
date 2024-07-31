package com.drowsynomad.mirrovision.presentation.navigation

import android.os.Parcelable
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitNavigationModel
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 21.06.2024)
 */

@Serializable
@Parcelize
data class StringParcel(val string: String) : Parcelable

@Serializable
sealed class Routes {
    val name = this.toString().substringAfterLast(".")

    @Serializable
    data object MainTestScreen: Routes()
    @Serializable
    data object SecondaryTestScreen: Routes()

    @Serializable
    data object SplashLoadingScreen: Routes()

    @Serializable
    data object IntroCategoriesScreen: Routes()
    @Serializable
    data class PresetHabitScreen(
        val rawCategoryList: StringParcel = StringParcel(emptyString())
    ): Routes()

    @Serializable
    data class CreateHabitScreen(
        val habit: HabitNavigationModel,
    ): Routes() {
        companion object { const val parameterKey = "HABIT_CREATED"}
    }

    @Serializable
    data class ChooseIconScreen(
        val color: CategoryMainColor,
    ): Routes() {
        companion object { const val parameterKey = "ICON_SELECTED"}
    }

    @Serializable
    data object DashboardScreen: Routes()

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

@Parcelize
data object EmptyParameters: Parcelable