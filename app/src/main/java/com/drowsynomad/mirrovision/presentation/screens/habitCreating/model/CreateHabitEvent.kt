package com.drowsynomad.mirrovision.presentation.screens.habitCreating.model

import com.drowsynomad.mirrovision.domain.models.RegularityType
import com.drowsynomad.mirrovision.presentation.core.components.models.DayUI
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.Time
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */

sealed class CreateHabitEvent: UiEvent {
    data class ConfigureStateForHabit(val habit: HabitUI): CreateHabitEvent()
    data class LoadExistedHabit(val habitId: Long?): CreateHabitEvent()
    data class SaveHabitDirectlyToStorageIfNeed(val habit: HabitUI): CreateHabitEvent()

    // Regularity
    data class RegularityAddNew(val cancellable: Boolean): CreateHabitEvent()
    data class RegularityRemove(val regularityId: Long): CreateHabitEvent()
    data class RegularityTimeChanged(val time: Time?, val useTime: Boolean, val regularityId: Long): CreateHabitEvent()
    data class RegularityDaysSelected(val days: DayUI, val regularityId: Long): CreateHabitEvent()
    data class RegularityTypeChanged(val type: RegularityType, val regularityId: Long): CreateHabitEvent()
}