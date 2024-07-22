package com.drowsynomad.mirrovision.presentation.screens.habitCreating.model

import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */

sealed class CreateHabitEvent: UiEvent {
    data class ConfigureStateForHabit(val habit: HabitUI): CreateHabitEvent()
}