package com.drowsynomad.mirrovision.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.HabitCategory
import com.drowsynomad.mirrovision.presentation.core.components.colorPicker.ColoredCategory
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.screens.home.model.HomeEvent
import com.drowsynomad.mirrovision.presentation.screens.home.model.HomeState

/**
 * @author Roman Voloshyn (Created on 31.07.2024)
 */

@Composable
fun HomeScreen(
    viewModel: HomeVM,
    onEditHabitClick: (HabitUI) -> Unit
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(HomeEvent.LoadTodayCategories )}
    ) {
        HomeContent(
            state = it,
            onEditHabitClick = onEditHabitClick,
            onEditCategory = { viewModel.handleUiEvent(HomeEvent.UpdateCategory(it)) },
            onHabitClick = { viewModel.handleUiEvent(HomeEvent.FillHabitCell(it)) },
            onLongHabitClick = { viewModel.handleUiEvent(HomeEvent.RemoveHabitCell(it)) }
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onEditCategory: (ColoredCategory) -> Unit,
    onEditHabitClick: (HabitUI) -> Unit,
    onHabitClick: (HabitUI) -> Unit,
    onLongHabitClick: (HabitUI) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.offset(y = (-20).dp),
        contentPadding = PaddingValues(bottom = 120.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(state.categoriesWithHabits, key = { category -> category.id }) {
            HabitCategory(
                category = it,
                onEditCategory = onEditCategory,
                onEditHabit = onEditHabitClick,
                onHabitClick = onHabitClick,
                onLongHabitClick = onLongHabitClick)
        }
    }
}