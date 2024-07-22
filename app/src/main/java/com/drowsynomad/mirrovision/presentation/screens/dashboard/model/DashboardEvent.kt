package com.drowsynomad.mirrovision.presentation.screens.dashboard.model

import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 17.07.2024)
 */

sealed class DashboardEvent: UiEvent {
    data object LoadCategories: DashboardEvent()
    data class FillHabitCell(val habitUI: HabitUI): DashboardEvent()
    data class RemoveHabitCell(val habitUI:HabitUI): DashboardEvent()
}