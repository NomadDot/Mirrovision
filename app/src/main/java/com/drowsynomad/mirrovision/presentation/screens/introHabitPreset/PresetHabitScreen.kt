package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.components.HabitCategory
import com.drowsynomad.mirrovision.presentation.core.components.Toolbar
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitState

/**
 * @author Roman Voloshyn (Created on 30.06.2024)
 */

@Composable
fun PresetHabitScreen(
    categories: List<CategoryUI>,
    viewModel: PresetHabitVM,
    onCreateHabit: (CategoryAssets) -> Unit,
    onBackNavigation: Navigation
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(PresetHabitEvent.PresetCategories(categories)) }
    ) {
        PresetHabitContent(
            it,
            onBackNavigation = onBackNavigation,
            onCreateHabit = onCreateHabit
        )
    }
}

@Composable
fun PresetHabitContent(
    state: PresetHabitState,
    modifier: Modifier = Modifier,
    onCreateHabit: (CategoryAssets) -> Unit,
    onBackNavigation: Navigation
) {
    Column {
        Toolbar(text = stringResource(R.string.label_my_habits), onBackClick = onBackNavigation::invoke)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(state.categories, key = { item -> item.id }) {
                HabitCategory(category = it, onCreateHabit = onCreateHabit)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PresetHabitContent(state = PresetHabitState(), onCreateHabit = {}) {}
}