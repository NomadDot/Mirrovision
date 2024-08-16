package com.drowsynomad.mirrovision.domain.models

import androidx.annotation.StringRes
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.components.models.ExpandableButtonContent

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */
data class Locale(
    val id: String = emptyString(),
    var isSelected: Boolean = false
) {
    fun toExpandableButtonItem(@StringRes localizedTitle: Int): ExpandableButtonContent =
        ExpandableButtonContent(id = id, title = localizedTitle, initialSelection = isSelected)
}