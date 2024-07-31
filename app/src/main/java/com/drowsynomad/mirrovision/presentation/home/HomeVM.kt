package com.drowsynomad.mirrovision.presentation.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.data.database.entities.CategoryAndHabits
import com.drowsynomad.mirrovision.data.database.entities.HabitEntity
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.domain.models.Category
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.home.model.HomeEvent
import com.drowsynomad.mirrovision.presentation.home.model.HomeState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */
@HiltViewModel
class HomeVM @Inject constructor(
    private val categoryRepository: ICategoryRepository,
    private val habitRepository: IHabitRepository,
): StateViewModel<HomeState, HomeEvent, SideEffect>(
    HomeState()
) {
    override fun handleUiEvent(uiEvent: HomeEvent) {
        when(uiEvent) {
            is HomeEvent.FillHabitCell -> fillHabitCell(uiEvent.habitUI)
            HomeEvent.LoadTodayCategories -> createTodayCategories()
            is HomeEvent.RemoveHabitCell -> removeHabitCell(uiEvent.habitUI)
        }
    }

    private fun createTodayCategories() {
        viewModelScope.launch {
            categoryRepository.loadLocalCategoriesWithHabits()
                .map { it.toCategoryUI() }
                .collect { categories ->
                    updateState { it.copy(categoriesWithHabits = categories, isLoading = false) }
                }
        }
    }

    private fun updateHabitActivityCell(id: Long, newFilledCount: Int) {
        habitRepository.updateActivityCell(habitId = id, newFilledCount = newFilledCount)
    }

    private fun fillHabitCell(habitUI: HabitUI) {
        val habitInCategory = uiState.value.categoriesWithHabits
            .find { it.id == habitUI.attachedCategoryId  }
            ?.habits?.find { it == habitUI }

        habitInCategory?.let {
            if(it.strokeAmount.prefilledCellAmount < it.strokeAmount.cellAmount)
                launch(Dispatchers.IO) {
                    updateHabitActivityCell(habitUI.id, habitUI.stroke.prefilledCellAmount.inc())
                }
            it.incrementFilledCell()
        }
    }

    private fun removeHabitCell(habitUI: HabitUI) {
        val habitInCategory = uiState.value.categoriesWithHabits
            .find { it.id == habitUI.attachedCategoryId  }
            ?.habits?.find { it == habitUI }

        habitInCategory?.let {
            if(it.strokeAmount.prefilledCellAmount > 0)
                launch(Dispatchers.IO) {
                    updateHabitActivityCell(habitUI.id, habitUI.stroke.prefilledCellAmount.dec())
                }
            it.decrementFilledCell()
        }
    }

    private fun List<HabitEntity>.toHabitUI(): SnapshotStateList<HabitUI> = this.map {
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

    private fun List<CategoryAndHabits>.toCategoryUI(): List<CategoryUI> = this.map {
        CategoryUI(
            id = it.categoryEntity.id,
            name = it.categoryEntity.name,
            backgroundColor = CategoryMainColor.parse(it.categoryEntity.bgColor),
            iconRes = it.categoryEntity.icon,
            habits = it.habits.toHabitUI()
        )
    }
}