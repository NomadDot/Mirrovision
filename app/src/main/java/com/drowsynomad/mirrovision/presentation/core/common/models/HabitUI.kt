package com.drowsynomad.mirrovision.presentation.core.common.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

@Serializable
@Parcelize
data class HabitUI(
    val id: Long = 0,
    val name: String = emptyString(),
    val description: String = emptyString(),
    @DrawableRes val icon: Int = R.drawable.ic_add,
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    val attachedCategoryId: Int = 0,
    val stroke: StrokeAmountState = StrokeAmountState()
) : Parcelable {
    @IgnoredOnParcel
    val isDefaultIcon = icon == R.drawable.ic_add

    @IgnoredOnParcel
    val accentColor = backgroundColor.accent

    @IgnoredOnParcel
    var strokeAmount by mutableStateOf(stroke)

    fun incrementFilledCell() {
        if (strokeAmount.prefilledCellAmount < stroke.cellAmount)
            strokeAmount.let {
                return@let it.copy(
                    prefilledCellAmount = it.prefilledCellAmount.inc()
                ).also { strokeAmount = it }
            }
    }

    fun decrementFilledCell() {
        if (strokeAmount.prefilledCellAmount > 0)
            strokeAmount.let {
                return@let it.copy(
                    prefilledCellAmount = it.prefilledCellAmount.dec()
                ).also { strokeAmount = it }
            }
    }

    fun toHabit(): Habit = Habit(
        id = id,
        attachedCategoryId = attachedCategoryId,
        name = name,
        description = description,
        icon = icon,
        backgroundColor = backgroundColor,
        cellAmount = stroke.cellAmount,
        filledCellAmount = stroke.prefilledCellAmount
    )

    fun toCategoryAssets(): CategoryAssets =
        CategoryAssets(this.attachedCategoryId, this.backgroundColor)
}

@Serializable
@Parcelize
sealed class StrokeWidth(val width: Float = 25f) : Parcelable {
    data object Default : StrokeWidth()
    data class Custom(val customWidth: Float) : StrokeWidth(customWidth)
}

@Serializable
@Parcelize
data class StrokeAmountState(
    val cellAmount: Int = 1,
    val prefilledCellAmount: Int = 0,
    val filledColor: CategoryAccentColor = CategoryAccentColor.PurpleAccent
) : Parcelable