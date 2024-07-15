package com.drowsynomad.mirrovision.presentation.core.common.models

import androidx.annotation.DrawableRes
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlinx.serialization.Serializable
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

@Serializable
data class CategoryUI(
    val id: Int = Random.nextInt(),
    val name: String = emptyString(),
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    @DrawableRes val iconRes: Int = R.drawable.ic_add,
    val habits: List<HabitUI> = emptyList()
)