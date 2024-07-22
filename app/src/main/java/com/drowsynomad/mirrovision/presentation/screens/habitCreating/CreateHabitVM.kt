package com.drowsynomad.mirrovision.presentation.screens.habitCreating

import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */

@HiltViewModel
class CreateHabitVM  @Inject constructor(

): StateViewModel<CreateHabitState, CreateHabitEvent, SideEffect>(
    CreateHabitState(null)
) {
    override fun handleUiEvent(uiEvent: CreateHabitEvent) {
        when(uiEvent) {
            is CreateHabitEvent.ConfigureStateForHabit -> updateState { it.copy(habitUI = uiEvent.habit) }
        }
    }

}