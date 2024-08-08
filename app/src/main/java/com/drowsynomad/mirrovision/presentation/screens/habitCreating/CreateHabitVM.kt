package com.drowsynomad.mirrovision.presentation.screens.habitCreating

import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.domain.habit.IHabitRecordingRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRegularityRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.domain.models.RegularityType
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.common.models.DayUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.RegularityContentUI
import com.drowsynomad.mirrovision.presentation.core.components.Time
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitState
import com.drowsynomad.mirrovision.presentation.utils.IStringConverterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */

@HiltViewModel
class CreateHabitVM@Inject constructor(
    private val habitRepository: IHabitRepository,
    private val habitRecordingRepository: IHabitRecordingRepository,
    private val habitRegularityRepository: IHabitRegularityRepository,
    private val stringConverterManager: IStringConverterManager
): StateViewModel<CreateHabitState, CreateHabitEvent, SideEffect>(
    CreateHabitState(null)
) {
    override fun handleUiEvent(uiEvent: CreateHabitEvent) {
        when(uiEvent) {
            is CreateHabitEvent.ConfigureStateForHabit -> updateState {
                if(it.habitUI != null ) it.copy(
                    habitUI = it.habitUI.copy(icon = uiEvent.habit.icon)
                )
                else it.copy(habitUI = uiEvent.habit)
            }
            is CreateHabitEvent.SaveHabitDirectlyToStorage ->
                saveHabitDirectlyToStorage(habitUI = uiEvent.habit)

            is CreateHabitEvent.RegularityAddNew -> addNewRegularityBlock(uiEvent.cancellable)
            is CreateHabitEvent.RegularityDaysSelected -> updateRegularityDays(uiEvent.days, uiEvent.regularityId)
            is CreateHabitEvent.RegularityRemove -> removeRegularity(uiEvent.regularityId)
            is CreateHabitEvent.RegularityTimeChanged -> updateRegularityTime(uiEvent.time, uiEvent.useTime, uiEvent.regularityId)
            is CreateHabitEvent.RegularityTypeChanged -> updateRegularityType(uiEvent.type, uiEvent.regularityId)
        }
    }

    private fun saveHabitDirectlyToStorage(habitUI: HabitUI) {
        if(!habitUI.isDefaultIcon)
            launch {
                val presetStroke = habitUI.stroke
                habitRepository.createNewOrUpdateHabit(
                    habitUI.copy(
                        stroke = presetStroke.copy(
                            prefilledCellAmount =
                                if (presetStroke.prefilledCellAmount >= presetStroke.cellAmount) presetStroke.cellAmount
                                else presetStroke.prefilledCellAmount)
                    ).toHabit()
                )
                habitRegularityRepository
            }
    }

    private fun addNewRegularityBlock(cancellable: Boolean = false) {
        uiState.value.habitUI?.regularityState?.add(
            RegularityContentUI.getDefaultRegularity(
                stringConverterManager.getStringArray(R.array.weekly_days).toList(),
                cancellable
            )
        )
    }

    private fun updateRegularityDays(
        clickedDay: DayUI,
        regularityId: Int,
    ) {
        uiState.value.habitUI?.regularityState
            ?.find { it.id == regularityId }
            ?.also {
                when(it.stateType.value) {
                    RegularityType.MonthlyType ->
                        it.month.days.find { it.dayPosition == clickedDay.dayPosition }
                            ?.also {
                                it.isSelected.value = it.isSelected.value.not()
                            }
                    is RegularityType.WeeklyType ->
                        it.week.days.find { it.dayPosition == clickedDay.dayPosition }
                            ?.also {
                                it.isSelected.value = it.isSelected.value.not()
                            }
                }
            }
    }

    private fun removeRegularity(regularityId: Int) {
        uiState.value.habitUI?.regularityState
            ?.find { it.id == regularityId }
            ?.also {
                uiState.value.habitUI?.regularityState?.remove(it)
            }
    }

    private fun updateRegularityTime(
        time: Time?,
        useTime: Boolean,
        regularityId: Int
    ) {
        uiState.value.habitUI?.regularityState
            ?.find { it.id == regularityId }
            ?.also {
                val selectedTime = time?.formattedTime ?: it.presetTime
                it.stateTime.value = selectedTime
                it.stateUseTime.value = useTime
            }
    }

    private fun updateRegularityType(
        type: RegularityType,
        regularityId: Int
    ) {
        uiState.value.habitUI?.regularityState
            ?.find { it.id == regularityId }
            ?.also {
                it.stateType.value = type
            }
    }
}