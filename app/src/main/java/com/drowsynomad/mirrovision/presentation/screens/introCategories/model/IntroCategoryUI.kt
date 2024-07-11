package com.drowsynomad.mirrovision.presentation.screens.introCategories.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateOf
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

data class IntroCategoryUI(
    val name: String = emptyString(),
    @DrawableRes val icon: Int = 0,
    val color: CategoryMainColor = CategoryMainColor.Blue,
    val isSelected: Boolean = false
) {
    val id: Int = Random.nextInt()
    val selection = mutableStateOf(isSelected)

    fun toCategoryUI(): CategoryUI {
        return CategoryUI(name = name, backgroundColor = color, iconRes = icon)
    }
}
