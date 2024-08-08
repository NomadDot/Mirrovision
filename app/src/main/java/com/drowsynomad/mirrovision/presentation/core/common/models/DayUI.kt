package com.drowsynomad.mirrovision.presentation.core.common.models

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 08.08.2024)
 */

data class Days(
    val days: List<DayUI> = emptyList()
) {
    fun toNavigationDays(): NavigationDays =
        NavigationDays(this.days.map { it.toNavigationModel() })
}

data class DayUI(
    val dayPosition: Int,
    val dayName: String,
    val initialSelection: Boolean = false
) {
    @IgnoredOnParcel
    val isSelected = mutableStateOf(initialSelection)

    fun toNavigationModel(): NavigationDayModel {
        return NavigationDayModel(
            dayPosition = dayPosition, dayName = dayName, initialSelection = isSelected.value
        )
    }
}

@Serializable
@Parcelize
data class NavigationDays(
    val days: List<NavigationDayModel> = emptyList()
): Parcelable {
    fun toDaysUI(): Days {
        return Days(this.days.map { it.toDayUI() })
    }
}

@Serializable
@Parcelize
data class NavigationDayModel(
    val dayPosition: Int,
    val dayName: String,
    val initialSelection: Boolean = false
): Parcelable {
    fun toDayUI(): DayUI {
        return DayUI(
            dayPosition = dayPosition, dayName = dayName, initialSelection = initialSelection
        )
    }
}