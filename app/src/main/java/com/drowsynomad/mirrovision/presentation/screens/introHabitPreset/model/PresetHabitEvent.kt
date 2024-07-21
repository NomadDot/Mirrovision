package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model

import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 30.06.2024)
 */

sealed class PresetHabitEvent: UiEvent {
    data class PresetCategories(val categories: List<CategoryUI>): PresetHabitEvent()
    data object SaveCategories: PresetHabitEvent()
}