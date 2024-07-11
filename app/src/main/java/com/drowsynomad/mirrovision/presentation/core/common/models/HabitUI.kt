package com.drowsynomad.mirrovision.presentation.core.common.models

import androidx.annotation.DrawableRes
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

@Serializable
data class HabitUI(
    val id: Long = 0,
    val name: String = emptyString(),
    val description: String = emptyString(),
    @DrawableRes val icon: Int = R.drawable.ic_add,
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    val accentColor: CategoryAccentColor = backgroundColor.accent
) {
    val isDefaultIcon = icon == R.drawable.ic_add
}