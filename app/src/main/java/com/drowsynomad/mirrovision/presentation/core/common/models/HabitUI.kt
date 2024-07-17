package com.drowsynomad.mirrovision.presentation.core.common.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
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
data class StrokeAmount(
    val cellAmount: Int = 1,
    val prefilledCellAmount: Int = 0,
    val filledColor: CategoryAccentColor = CategoryAccentColor.PurpleAccent
): Parcelable

@Serializable
@Parcelize
data class HabitUI(
    val id: Long = 0,
    val name: String = emptyString(),
    val description: String = emptyString(),
    @DrawableRes val icon: Int = R.drawable.ic_add,
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    val attachedCategoryId: Int = 0,
    val stroke: StrokeAmount = StrokeAmount()
): Parcelable {
    @IgnoredOnParcel
    val isDefaultIcon = icon == R.drawable.ic_add
    @IgnoredOnParcel
    val accentColor = backgroundColor.accent
}