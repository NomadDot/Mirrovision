package com.drowsynomad.mirrovision.presentation.core.common.models

import android.os.Parcelable
import androidx.compose.runtime.mutableStateOf
import com.drowsynomad.mirrovision.core.emptyString
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
    val id: Int = Random.nextInt(),
    var presetTime: String = emptyString(),
    var useTime: Boolean = false,
    val cancellable: Boolean = false,
    var type: RegularityType = RegularityType.WeeklyType(),
    var week: Days = Days(),
    var month: Days = Days(),
) {
    @IgnoredOnParcel
    var stateTime = mutableStateOf(presetTime)
    @IgnoredOnParcel
    var stateUseTime = mutableStateOf(useTime)
    @IgnoredOnParcel
    var stateType = mutableStateOf(type)

    companion object {
        fun getDefaultRegularity(localizedDayNames: List<String>, cancellable: Boolean = false): RegularityContentUI {
            val currentLocalTime = DateTime.now().toLocalTime()
            val currentTime = "${currentLocalTime.hourOfDay}:${currentLocalTime.minuteOfHour}"
                .formatTime()

            val weeklyDays = fillWeeklyDays(localizedDayNames)
            val monthlyDays = fillMonthlyDays()

            return RegularityContentUI(presetTime = currentTime, week = Days(weeklyDays), month = Days(monthlyDays), cancellable = cancellable)
        }
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
}

data class Regularities(
    val regularityList: List<RegularityContentUI> = emptyList()
)

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
    val id: Int = Random.nextInt(),
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
