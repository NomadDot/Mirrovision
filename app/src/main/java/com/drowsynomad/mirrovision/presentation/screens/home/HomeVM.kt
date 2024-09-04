package com.drowsynomad.mirrovision.presentation.screens.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.drowsynomad.mirrovision.core.generateDayId
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRecordingRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColoredCategory
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.screens.home.model.HomeEvent
import com.drowsynomad.mirrovision.presentation.screens.home.model.HomeState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.calendar
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

@HiltViewModel
class HomeVM @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val habitRepository: IHabitRepository,
    private val habitRecordingsRepository: IHabitRecordingRepository
): StateViewModel<HomeState, HomeEvent, SideEffect>(
    HomeState()
) {
    override fun handleUiEvent(uiEvent: HomeEvent) {
        when(uiEvent) {
            is HomeEvent.FillHabitCell -> fillHabitCell(uiEvent.habitUI)
            HomeEvent.LoadTodayCategories -> loadTodayCategories()
            is HomeEvent.RemoveHabitCell -> removeHabitCell(uiEvent.habitUI)
            is HomeEvent.UpdateCategory -> updateCategory(uiEvent.category)
        }
    }

    private val dayId by lazy { generateDayId() }

    private fun loadTodayCategories() {
        launch {
            val currentDayInWeek = calendar.dayOfWeek
            val currentDayInMonth = calendar.dayOfMonth

            if(habitRecordingsRepository.isDailyRecordNotConfigured(dayId))
                habitRecordingsRepository.createDailyRecordings(
                    currentDayInWeek = currentDayInWeek,
                    currentDayInMonth = currentDayInMonth
                )

            val categories = categoryRepository.loadCategoriesForDay(dayId)
            updateState { it.copy(categoriesWithHabits = categories, isLoading = false) }
        }
    }

    private fun updateCategory(coloredCategory: ColoredCategory) {
        launch() {
            categoryRepository.updateCategory(
                coloredCategory.id, coloredCategory.color.main, coloredCategory.icon, coloredCategory.name
            )
            handleUiEvent(HomeEvent.LoadTodayCategories)
        }
    }

    private fun updateHabitActivityCell(
        id: Long,
        newFilledCount: Int,
        dayId: Long
    ) {
        habitRepository.updateActivityCell(
            habitId = id,
            newFilledCount = newFilledCount,
            dayId
        )
    }

    private fun fillHabitCell(habitUI: HabitUI) {
        val incrementedValue = habitUI.strokeAmount.prefilledCellAmount.inc()

        val habitInCategory = uiState.value.categoriesWithHabits
            .find { it.id == habitUI.attachedCategoryId  }
            ?.habits?.find { it == habitUI }

        habitInCategory?.let {
            if(it.strokeAmount.prefilledCellAmount < it.strokeAmount.cellAmount)
                launch(Dispatchers.IO) {
                    updateHabitActivityCell(habitUI.id, incrementedValue, dayId)
                }
            it.incrementFilledCell()
        }
    }

    private fun removeHabitCell(habitUI: HabitUI) {
        val decrementedValue = habitUI.strokeAmount.prefilledCellAmount.dec()
        val habitInCategory = uiState.value.categoriesWithHabits
            .find { it.id == habitUI.attachedCategoryId  }
            ?.habits?.find { it == habitUI }

        habitInCategory?.let {
            if(it.strokeAmount.prefilledCellAmount > 0)
                launch(Dispatchers.IO) {
                    updateHabitActivityCell(
                        habitUI.id,
                        decrementedValue,
                        dayId
                    )
                }
            it.decrementFilledCell()
        }
    }

    private fun List<HabitEntity>.toHabitUI(): SnapshotStateList<HabitUI> =
        this.map {
            val categoryColor = CategoryMainColor.parse(it.bgColor)
            HabitUI(
                id = it.id,
                name = it.name,
                description = it.description,
                icon = it.icon,
                backgroundColor = categoryColor,
                attachedCategoryId = it.categoryId,
                stroke = StrokeAmountState(
                    it.amount.cellAmount,
                    it.amount.prefilledCellAmount,
                    categoryColor.accent
                )
            )
        }.toMutableStateList()
}