package com.drowsynomad.mirrovision.presentation.dialogs.settings.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.core.common.models.ExpandableButtonContent

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

data class SettingsDialogState(
    val languageContent: SnapshotStateList<ExpandableButtonContent> = mutableStateListOf(),
    val themeContent: SnapshotStateList<ExpandableButtonContent> = mutableStateListOf()
): UiState