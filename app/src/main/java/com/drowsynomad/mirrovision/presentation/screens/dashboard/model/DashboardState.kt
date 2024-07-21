package com.drowsynomad.mirrovision.presentation.screens.dashboard.model

import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI

/**
 * @author Roman Voloshyn (Created on 17.07.2024)
 */

data class DashboardState(
    val categoriesWithHabits: List<CategoryUI> = emptyList(),
    val isLoading: Boolean = false
): UiState