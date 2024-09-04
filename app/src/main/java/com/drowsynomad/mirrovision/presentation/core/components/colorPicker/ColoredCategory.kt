package com.drowsynomad.mirrovision.presentation.core.components.colorPicker

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.screens.introCategories.model.IntroCategoryUI
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 26.06.2024)
 */

data class ColoredCategory(
    val id: Int = Random.nextInt(),
    val color: ColorShades = ColorShades(),
    val name: String = emptyString(),
    @DrawableRes val icon: Int = 0,
    val initialSelection: Boolean = false,
) {
    var selected by mutableStateOf(initialSelection)

    fun toIntroCategory(initialSelection: Boolean = false): IntroCategoryUI {
        return IntroCategoryUI(name = name, isSelected = initialSelection, color = color.main, icon = icon)
    }
}