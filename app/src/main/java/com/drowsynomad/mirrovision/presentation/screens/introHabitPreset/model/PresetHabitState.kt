package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model

import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI

/**
 * @author Roman Voloshyn (Created on 30.06.2024)
 */

data class PresetHabitState(
    val categories: List<CategoryUI> = emptyList(),
    val isNextNavigation: Boolean = false
): UiState
