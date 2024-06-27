package com.drowsynomad.mirrovision.presentation.core.components.colorPicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.drowsynomad.mirrovision.core.emptyString
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 26.06.2024)
 */

data class ColoredCategory(
    val color: ColorShades = ColorShades(),
    val name: String = emptyString(),
    val initialSelection: Boolean = false,
) {
    val id: Int = Random.nextInt()
    var selected by mutableStateOf(initialSelection)
}