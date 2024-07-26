package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.DefaultProgress
import com.drowsynomad.mirrovision.presentation.core.components.DefaultToolbar
import com.drowsynomad.mirrovision.presentation.core.components.HabitCategory
import com.drowsynomad.mirrovision.presentation.core.components.PrimaryButton
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.navigation.Routes
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitEvent.SaveCategories
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitState
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetSideEffect
import com.drowsynomad.mirrovision.presentation.utils.clearRoute

/**
 * @author Roman Voloshyn (Created on 30.06.2024)
 */

@Composable
fun PresetHabitScreen(
    categories: List<CategoryUI>,
    viewModel: PresetHabitVM,
    onCreateHabit: (HabitUI) -> Unit,
    onBackNavigation: Navigation,
    onNextNavigation: Navigation
) {
    StateContent(
        viewModel = viewModel,
        sideEffect = object: PresetSideEffect {
            override fun navigateNext() = onNextNavigation.invoke()
        },
        launchedEffect = { viewModel.handleUiEvent(PresetHabitEvent.PresetCategories(categories)) }
    ) {
        PresetHabitContent(
            it,
            onSaveCategories = { viewModel.handleUiEvent(SaveCategories) },
            onBackNavigation = onBackNavigation,
            onCreateHabit = onCreateHabit
        )
    }
}

@Composable
fun PresetHabitContent(
    state: PresetHabitState,
    modifier: Modifier = Modifier,
    onSaveCategories: () -> Unit,
    onCreateHabit: (HabitUI) -> Unit,
    onBackNavigation: Navigation
) {
    val isSavingProgressShown = remember {
        mutableStateOf(false)
    }

    if(isSavingProgressShown.value)
        DefaultProgress()

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            DefaultToolbar(
                text = stringResource(R.string.label_my_habits),
                onBackClick = onBackNavigation::invoke
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                items(state.categories, key = { item -> item.id }) {
                    HabitCategory(category = it) { habit ->
                        onCreateHabit.invoke(habit)
                    }
                }
            }
        }
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.button_save),
            isEnabled = state.categories.none { it.isFirstHabitPreset }
        ) {
            isSavingProgressShown.value = true
            onSaveCategories.invoke()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PresetHabitContent(state = PresetHabitState(), onCreateHabit = { }, onSaveCategories = {}) {}
}