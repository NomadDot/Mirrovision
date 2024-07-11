package com.drowsynomad.mirrovision.presentation.core.components.colorPicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 26.06.2024)
 */

data class ColorShades(
    val main: CategoryMainColor = CategoryMainColor.Blue,
    val accent: CategoryAccentColor = CategoryAccentColor.BlueAccent,
    val initialSelection: Boolean = false,
) {
    val id: Int = Random.nextInt()
    var selected by mutableStateOf(initialSelection)
}