package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset

import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 30.06.2024)
 */

@HiltViewModel
class PresetHabitVM @Inject constructor(

): StateViewModel<PresetHabitState, PresetHabitEvent>(PresetHabitState()) {
    override fun handleUiEvent(uiEvent: PresetHabitEvent) {
        when(uiEvent) {
            is PresetHabitEvent.PresetCategories -> presetCategories(uiEvent.categories)
        }
    }

    private fun presetCategories(categories: List<CategoryUI>) {
        val categoriesWithSingleHabit = categories.map { it.copy(habits = listOf(HabitUI(backgroundColor = it.backgroundColor))) }
        uiState.value = PresetHabitState(categories = categoriesWithSingleHabit)
    }
}