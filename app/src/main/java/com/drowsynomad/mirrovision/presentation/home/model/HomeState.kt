package com.drowsynomad.mirrovision.presentation.home.model

import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

data class HomeState(
    val categoriesWithHabits: List<CategoryUI> = emptyList(),
    val isLoading: Boolean = false
): UiState