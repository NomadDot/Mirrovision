package com.drowsynomad.mirrovision.presentation.screens.introCategories.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.drowsynomad.mirrovision.presentation.core.common.UiState

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

data class IntroCategoriesState(
    val isProgress: Boolean = false,
    val categories: SnapshotStateList<IntroCategoryUI> = mutableStateListOf()
): UiState