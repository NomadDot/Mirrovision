package com.drowsynomad.mirrovision.presentation.screens.habitCreating

import com.drowsynomad.mirrovision.domain.habit.IHabitRecordingRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRegularityRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitState
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
): StateViewModel<CreateHabitState, CreateHabitEvent, SideEffect>(
    CreateHabitState(null)
) {
    override fun handleUiEvent(uiEvent: CreateHabitEvent) {
        when(uiEvent) {
            is CreateHabitEvent.ConfigureStateForHabit -> updateState { it.copy(habitUI = uiEvent.habit) }
            is CreateHabitEvent.SaveHabitDirectlyToStorage ->
                saveHabitDirectlyToStorage(habitUI = uiEvent.habit)
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
}