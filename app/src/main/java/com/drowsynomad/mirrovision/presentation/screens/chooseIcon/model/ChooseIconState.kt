package com.drowsynomad.mirrovision.presentation.screens.chooseIcon.model

import androidx.annotation.DrawableRes
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.IconsInCategory

/**
 * @author Roman Voloshyn (Created on 22.07.2024)
 */
data class ChooseIconState(
    val icons: List<IconsInCategory> = emptyList(),
    val isSavingEnabled: Boolean = false,
    @DrawableRes val selectedIcon: Int? = null,
    val isLoading: Boolean = false,
): UiState