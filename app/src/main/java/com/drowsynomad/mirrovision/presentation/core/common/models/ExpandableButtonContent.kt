package com.drowsynomad.mirrovision.presentation.core.common.models

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import com.drowsynomad.mirrovision.core.emptyString

data class ExpandableButtonContent(
    val id: String = emptyString(),
    @StringRes val title: Int,
    val initialSelection: Boolean = false,
) {
    var isSelected = mutableStateOf(initialSelection)
}