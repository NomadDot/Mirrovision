package com.drowsynomad.mirrovision.presentation.screens.habitCreating.model

import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */
data class CreateHabitState(
    val habitUI: HabitUI? = null
): UiState