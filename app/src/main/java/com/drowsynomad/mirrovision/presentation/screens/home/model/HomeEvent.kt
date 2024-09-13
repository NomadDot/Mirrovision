package com.drowsynomad.mirrovision.presentation.screens.home.model

import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColoredCategory
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

sealed class HomeEvent: UiEvent {
    data object LoadTodayCategories: HomeEvent()
    data class UpdateCategory(val category: ColoredCategory): HomeEvent()
    data class FillHabitCell(val habitUI: HabitUI): HomeEvent()
    data class RemoveHabitCell(val habitUI: HabitUI): HomeEvent()
}