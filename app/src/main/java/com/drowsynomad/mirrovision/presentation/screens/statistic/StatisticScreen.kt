package com.drowsynomad.mirrovision.presentation.screens.statistic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.DefaultProgress
import com.drowsynomad.mirrovision.presentation.core.components.DoubleSelector
import com.drowsynomad.mirrovision.presentation.core.components.HabitProgressUI
import com.drowsynomad.mirrovision.presentation.core.components.ProgressHabit
import com.drowsynomad.mirrovision.presentation.core.components.StatisticCategory
import com.drowsynomad.mirrovision.presentation.core.components.TextSeparator
import com.drowsynomad.mirrovision.presentation.core.components.WeeklyChart
import com.drowsynomad.mirrovision.presentation.core.components.WeeklyProgress
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticState
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticsEvent

/**
 * @author Roman Voloshyn (Created on 21.08.2024)
 */

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(StatisticsEvent.CreateStatistics) }
    ) { state ->
        Column {
            val isWeekly = rememberSaveable { mutableStateOf(true) }

            if(state.isLoading)
                DefaultProgress()
            else {
                DoubleSelector(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 20.dp),
                    leftButtonLabel = stringResource(R.string.label_weekly_statistics),
                    rightButtonLabel = stringResource(R.string.label_detailed_statistics),
                    onLeftButtonClick = { isWeekly.value = true },
                    onRightButtonClick = { isWeekly.value = false }
                )
                if (isWeekly.value)
                    WeeklyStatisticContent(state)
                else
                    DetailedStatisticContent(state.detailedHabits)
            }
        }
    }
}

@Composable
private fun WeeklyStatisticContent(
    state: StatisticState
) {
    Box {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            TextSeparator(
                modifier = Modifier,
                text = stringResource(R.string.label_done_in_a_week)
            )

            Spacer(modifier = Modifier.height(15.dp))
            WeeklyProgress(percent = state.weeklyProgress)
            Spacer(modifier = Modifier.height(20.dp))
            WeeklyChart(modifier = Modifier, chartData = state.chartData)
            Spacer(modifier = Modifier.height(20.dp))

            state.categoryStatistic.forEach {
                StatisticCategory(stat = it)
                Spacer(modifier = Modifier.height(15.dp))
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
private fun DetailedStatisticContent(
    habits: List<HabitProgressUI>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        items(habits, key = { it.habitId }) {
            ProgressHabit(habitProgressUI = it)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    WeeklyStatisticContent(StatisticState())
}