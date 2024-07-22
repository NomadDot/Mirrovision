package com.drowsynomad.mirrovision.presentation.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.BottomNavigationBar
import com.drowsynomad.mirrovision.presentation.core.components.HabitCategory
import com.drowsynomad.mirrovision.presentation.core.components.Toolbar
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.dashboard.model.DashboardEvent
import com.drowsynomad.mirrovision.presentation.screens.dashboard.model.DashboardState

/**
 * @author Roman Voloshyn (Created on 17.07.2024)
 */

@Composable
fun DashboardScreen(
    viewModel: DashboardVM,
    onBackNavigation: Navigation
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(DashboardEvent.LoadCategories)}
    ) {
        DashboardContent(
            it,
            onHabitClick = { viewModel.handleUiEvent(DashboardEvent.FillHabitCell(it)) },
            onLongHabitClick = { viewModel.handleUiEvent(DashboardEvent.RemoveHabitCell(it)) },
            onBackNavigation = onBackNavigation)
    }
}

@Composable
fun DashboardContent(
    state: DashboardState,
    onHabitClick: (HabitUI) -> Unit,
    onLongHabitClick: (HabitUI) -> Unit,
    onBackNavigation: Navigation
) {
    Scaffold(
        topBar = {
            Toolbar(text = stringResource(id = R.string.label_my_habits), onBackClick = onBackNavigation)
        },
        bottomBar = { BottomNavigationBar(modifier = Modifier.padding(horizontal = 20.dp)) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
            contentPadding = PaddingValues(bottom = 120.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(state.categoriesWithHabits, key = { category -> category.id }) {
                HabitCategory(
                    category = it,
                    onHabitClick = onHabitClick,
                    onLongHabitClick = onLongHabitClick
                )
            }
        }
    }
}