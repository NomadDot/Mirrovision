package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset

import androidx.compose.runtime.mutableStateListOf
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.user.IUserRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitState
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 30.06.2024)
 */

@HiltViewModel
class PresetHabitVM @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val userRepository: IUserRepository
): StateViewModel<PresetHabitState, PresetHabitEvent, PresetSideEffect>(PresetHabitState()) {
    override fun handleUiEvent(uiEvent: PresetHabitEvent) {
        when(uiEvent) {
            is PresetHabitEvent.PresetCategories -> presetCategories(uiEvent.categories)
            PresetHabitEvent.SaveCategories -> saveCategories()
        }
    }

    private fun saveCategories() {
        launch(Dispatchers.IO) {
            val categories = uiState.value.categories
            val habits: MutableList<HabitUI> = mutableListOf()

            categories.forEach { habits += it.habits }

            categoryRepository.saveCategoriesPreset(
                categories = categories.map(CategoryUI::toCategory),
                habits = habits.filter { !it.isDefaultIcon }.map(HabitUI::toHabit)
            )

            withContext(Dispatchers.Main) {
                userRepository.setUserFinishPreset()
                sideEffect?.navigateNext()
            }
        }
    }

    private fun getPresetHabit(category: CategoryUI) =
        HabitUI(
            backgroundColor = category.backgroundColor,
            attachedCategoryId = category.id,
            stroke = StrokeAmountState(filledColor = category.backgroundColor.accent)
        )

    private fun presetCategories(categories: List<CategoryUI>) {
        val categoriesWithSingleHabit = categories.map {category ->
            category.copy(
                habits =
                    if(category.isPresetCategory)
                        mutableStateListOf(getPresetHabit(category))
                    else {
                        category.habits += getPresetHabit(category)
                        category.habits
                    }
            )
        }

        uiState.value = PresetHabitState(categories = categoriesWithSingleHabit)
    }
}