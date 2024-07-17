package com.drowsynomad.mirrovision.presentation.screens.introHabitPreset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.components.HabitCategory
import com.drowsynomad.mirrovision.presentation.core.components.PrimaryButton
import com.drowsynomad.mirrovision.presentation.core.components.Toolbar
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.introHabitPreset.model.PresetHabitState
import com.drowsynomad.mirrovision.presentation.theme.ShadowColor

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
    val isSavingProgressShown = remember {
        mutableStateOf(false)
    }

    if(isSavingProgressShown.value)
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .align(Alignment.Center)
                    .shadow(30.dp, spotColor = ShadowColor),
            )
        }

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            Toolbar(
                text = stringResource(R.string.label_my_habits),
                onBackClick = onBackNavigation::invoke
            )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(bottom = 120.dp)
            ) {
                items(state.categories, key = { item -> item.id }) {
                    HabitCategory(category = it, onCreateHabit = onCreateHabit)
                }
            }
        }
        PrimaryButton(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 16.dp)
                .align(Alignment.BottomCenter),
            text = stringResource(id = R.string.button_save),
            isEnabled = state.categories.none { it.isFirstHabitPreset }
        ) {
            isSavingProgressShown.value = true
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PresetHabitContent(state = PresetHabitState(), onCreateHabit = {}) {}
}