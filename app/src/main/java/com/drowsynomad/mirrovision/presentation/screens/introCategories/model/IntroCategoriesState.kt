package com.drowsynomad.mirrovision.presentation.screens.introCategories.model

import com.voloshynroman.zirkon.presentation.core.common.UiState

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

data class IntroCategoriesState(
    val isProgress: Boolean = false,
    val categories: List<IntroCategory> = emptyList()
): UiState