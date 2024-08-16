package com.drowsynomad.mirrovision.presentation.core.components.models

import androidx.annotation.DrawableRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

data class CategoryUI(
    val id: Int = Random.nextInt(),
    val name: String = emptyString(),
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    @DrawableRes val iconRes: Int = R.drawable.ic_add,
    val habits: SnapshotStateList<HabitUI> = mutableStateListOf(),
    val customizationEnable: Boolean = true
) {
    val isPresetCategory = habits.isEmpty()
    val isFirstHabitPreset = if(habits.isEmpty()) true else habits.first().isDefaultIcon

    fun toCategory(): Category = Category(
        id = id, name = name,
        icon = iconRes,
        bgColor = backgroundColor
    )
}