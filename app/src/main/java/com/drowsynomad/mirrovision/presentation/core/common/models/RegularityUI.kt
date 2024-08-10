package com.drowsynomad.mirrovision.presentation.core.common.models

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.HabitRegularities
import com.drowsynomad.mirrovision.domain.models.HabitRegularity
import com.drowsynomad.mirrovision.domain.models.RegularityType
import com.drowsynomad.mirrovision.presentation.utils.fillMonthlyDays
import com.drowsynomad.mirrovision.presentation.utils.fillWeeklyDays
import com.drowsynomad.mirrovision.presentation.utils.formatTime
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 08.08.2024)
 */

data class RegularityContentUI(
    val id: Long = Random.nextLong(),
    var presetTime: String = emptyString(),
    var useTime: Boolean = false,
    val cancellable: Boolean = false,
    var type: RegularityType = RegularityType.WeeklyType(),
    private val presetDays: List<Int> = emptyList(),
    var week: Days = Days(),
    var month: Days = Days(),
) {
    @IgnoredOnParcel
    var stateTime = mutableStateOf(presetTime)
    @IgnoredOnParcel
    var stateUseTime = mutableStateOf(useTime)
    @IgnoredOnParcel
    var stateType = mutableStateOf(type)

    val isFilled: Boolean
        get() = when(stateType.value) {
            RegularityType.MonthlyType -> month.days.any { it.isSelected.value }
            is RegularityType.WeeklyType -> week.days.any { it.isSelected.value }
        }

    fun fillDays(weekLabels: List<String>) {
        var weekPresetDays: List<Int>? = null
        var monthPresentDays: List<Int>? = null

        when(type) {
            RegularityType.MonthlyType -> monthPresentDays = presetDays
            is RegularityType.WeeklyType -> weekPresetDays = presetDays
        }

        week = fillWeeklyDays(weekLabels, weekPresetDays)
        month = fillMonthlyDays(monthPresentDays)
    }

    fun toNavigationModel(): RegularityNavigationModel =
        RegularityNavigationModel(
            id = id,
            presetTime = stateTime.value,
            useTime = stateUseTime.value,
            cancellable = cancellable,
            type = RegularityTypeNavigation(stateType.value.toString()),
            week = week.toNavigationDays(),
            month = month.toNavigationDays()
        )

    fun toDomain(habitId: Long): HabitRegularity =
        HabitRegularity(
            id = id,
            habitId = habitId,
            time = stateTime.value,
            useReminder = stateUseTime.value,
            type = stateType.value,
            selectedDays = if(stateType.value is RegularityType.WeeklyType) week.toDomain() else month.toDomain(),
        )

    companion object {
        fun getDefaultRegularity(localizedDayNames: List<String>, cancellable: Boolean = false): RegularityContentUI {
            val currentLocalTime = DateTime.now().toLocalTime()
            val currentTime = "${currentLocalTime.hourOfDay}:${currentLocalTime.minuteOfHour}"
                .formatTime()

            val weeklyDays = fillWeeklyDays(localizedDayNames)
            val monthlyDays = fillMonthlyDays()

            return RegularityContentUI(presetTime = currentTime, week = weeklyDays, month = monthlyDays, cancellable = cancellable)
        }
    }

}

data class Regularities(
    val regularityList: List<RegularityContentUI> = emptyList()
) {

    fun toDomain(habitId: Long): HabitRegularities {
        return HabitRegularities(this.regularityList.map { it.toDomain(habitId) })
    }
}

@Serializable
@Parcelize
data class NavigationRegularities(
    val regularityList: List<RegularityNavigationModel> = emptyList()
): Parcelable

@Serializable
@Parcelize
data class RegularityTypeNavigation(
    private val type: String
): Parcelable {
    fun toRegularityType(): RegularityType =
        RegularityType.toType(type)
}

@Serializable
@Parcelize
data class RegularityNavigationModel(
    val id: Long = Random.nextLong(),
    val presetTime: String = emptyString(),
    val useTime: Boolean = false,
    val cancellable: Boolean = false,
    val type: RegularityTypeNavigation,
    val week: NavigationDays = NavigationDays(),
    val month: NavigationDays = NavigationDays(),
): Parcelable {
    fun toRegulationUI(): RegularityContentUI =
        RegularityContentUI(
            id = id,
            presetTime = presetTime,
            useTime = useTime,
            cancellable = cancellable,
            type = type.toRegularityType(),
            week = week.toDaysUI(),
            month = month.toDaysUI()
        )
}
