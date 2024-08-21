package com.drowsynomad.mirrovision.presentation.core.components.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.Habit
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.accent
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

data class HabitUI(
    val id: Long = Random.nextLong(),
    val name: String = emptyString(),
    val description: String = emptyString(),
    val presetRegularities: Regularities = Regularities(),
    @DrawableRes val icon: Int = R.drawable.ic_add,
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    val attachedCategoryId: Int = 0,
    val stroke: StrokeAmountState = StrokeAmountState()
) {
    @IgnoredOnParcel
    val isDefaultIcon = icon == R.drawable.ic_add

    @IgnoredOnParcel
    val isDefaultHabit = icon == R.drawable.ic_add && name.isEmpty() && description.isEmpty()

    @IgnoredOnParcel
    val accentColor = backgroundColor.accent

    @IgnoredOnParcel
    var strokeAmount by mutableStateOf(stroke)

    @IgnoredOnParcel
    val regularityState: SnapshotStateList<RegularityContentUI> =
        presetRegularities.regularityList.toMutableStateList()

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

    fun toHabitNavigation(isForIntro: Boolean = false): HabitNavigationModel {
        return HabitNavigationModel(
            id = id,
            name = name,
            description = description,
            regularity = NavigationRegularities(
                presetRegularities.regularityList
                    .map { it.toNavigationModel() }
            ),
            icon = icon,
            backgroundColor = backgroundColor,
            attachedCategoryId = attachedCategoryId,
            stroke = strokeAmount,
            isForIntro = isForIntro
        )
    }
}

@Serializable
@Parcelize
data class HabitDTO(
    val habitNavigationModel: HabitNavigationModel? = null,
    val habitId: Long? = null
): Parcelable

@Serializable
@Parcelize
data class HabitNavigationModel(
    val id: Long,
    val name: String = emptyString(),
    val description: String = emptyString(),
    val regularity: NavigationRegularities = NavigationRegularities(),
    @DrawableRes val icon: Int = R.drawable.ic_add,
    val backgroundColor: CategoryMainColor = CategoryMainColor.Blue,
    val attachedCategoryId: Int = 0,
    val stroke: StrokeAmountState = StrokeAmountState(),
    val isForIntro: Boolean = false
) : Parcelable {
    fun toHabitUI(): HabitUI {
        return HabitUI(
            id = id,
            name = name,
            description = description,
            presetRegularities = Regularities(
                regularity.regularityList.map { it.toRegulationUI() }),
            icon = icon,
            backgroundColor = backgroundColor,
            attachedCategoryId = attachedCategoryId,
            stroke = stroke
        )
    }
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