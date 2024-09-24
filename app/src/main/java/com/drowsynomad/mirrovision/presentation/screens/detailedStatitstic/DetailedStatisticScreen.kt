package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.AdviceText
import com.drowsynomad.mirrovision.presentation.core.components.CategoryTitle
import com.drowsynomad.mirrovision.presentation.core.components.ChartSelector
import com.drowsynomad.mirrovision.presentation.core.components.DefaultProgress
import com.drowsynomad.mirrovision.presentation.core.components.IconColumn
import com.drowsynomad.mirrovision.presentation.core.components.ProgressChart
import com.drowsynomad.mirrovision.presentation.core.components.StatisticCalendar
import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment
import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment.MONTH
import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment.WEEK
import com.drowsynomad.mirrovision.presentation.core.components.models.StatisticSegment.YEAR
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticEvent
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticState
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.StatisticInfo
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

@Composable
fun DetailedStatisticScreen(
    viewModel: DetailedStatisticVM,
    onChangeAppBarColor: (CategoryAccentColor) -> Unit,
    habitId: Long,
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = {
            viewModel.handleUiEvent(DetailedStatisticEvent.LoadStatistic(habitId))
        }
    ) {
        if (it.isLoading)
            DefaultProgress()
        else {
            LaunchedEffect(key1 = Unit) { onChangeAppBarColor.invoke(it.color.accent) }
            DetailedStatisticContent(
                state = it,
                onLoadCalendarDays = { previousCount, segment ->
                    viewModel.handleUiEvent(
                        DetailedStatisticEvent.LoadCalendarDays(
                            habitId, previousCount, segment
                        )
                    )
                }
            )
        }

        DisposableEffect(key1 = Unit) {
            onDispose { onChangeAppBarColor(it.color.accent) }
        }
    }
}

@Composable
private fun DetailedStatisticContent(
    state: DetailedStatisticState,
    onLoadCalendarDays: (count: Int, segment: StatisticSegment) -> Unit,
) {
    val segment = rememberSaveable {
        mutableStateOf(WEEK)
    }
    val calendarDaysModifier = rememberSaveable { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .offset(y = (-15).dp)
            .verticalScroll(rememberScrollState())
    ) {
        NameStatistic(
            habitName = state.habitName,
            habitDescription = state.habitDescription,
            color = state.color,
            habitIcon = state.habitIcon
        )
        ChartSelector(
            color = state.color,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 15.dp)
        ) {
            segment.value = it
            calendarDaysModifier.intValue = 0
        }
        StatisticCalendar(
            modifier = Modifier.padding(top = 5.dp),
            segment = segment.value,
            weeklyData = state.weekly,
            monthlyData = state.monthly,
            yearlyData = state.year,
            color = state.color,
            onShowPreviousCalendarDaysClick = {
                calendarDaysModifier.intValue += 1
                onLoadCalendarDays.invoke(calendarDaysModifier.intValue, segment.value)
            },
            onShowNextCalendarDaysClick = {
                calendarDaysModifier.intValue -= 1
                onLoadCalendarDays.invoke(calendarDaysModifier.intValue, segment.value)
            },
        )
        DefaultStatistic(
            statistic = StatisticInfo.CompletedStatistic(
                times = when(segment.value) {
                    WEEK -> state.weekly.completedTime
                    MONTH -> state.monthly.completedTime
                    YEAR ->  state.year.completedTime
                }.toString()
            ),
            statisticSegment = segment.value,
            color = state.color
        )
        ProgressChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .padding(top = 15.dp)
                .padding(horizontal = 24.dp),
            segment = segment.value,
            progress = when (segment.value) {
                WEEK -> state.weekly.chartProgress
                MONTH -> state.monthly.chartProgress
                YEAR -> state.year.chartProgress
            },
            color = state.color
        )
        DefaultStatistic(
            statistic = StatisticInfo.PomodoroStatistic("1h:25m"),
            statisticSegment = segment.value,
            color = state.color
        )
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun NameStatistic(
    habitName: String = emptyString(),
    habitDescription: String = emptyString(),
    color: CategoryMainColor = CategoryMainColor.Purple,
    habitIcon: Int = R.drawable.ic_add,
) {
    IconColumn(
        icon = habitIcon,
        color = color,
    ) {
        CategoryTitle(
            text = habitName,
            color = color.accent.pureColor,
            modifier = Modifier.padding(end = 16.dp)
        )
        AdviceText(
            text = habitDescription,
            modifier = Modifier.padding(top = 5.dp, end = 16.dp),
            color = color.accent.pureColor
        )
    }
}

@Composable
private fun DefaultStatistic(
    statistic: StatisticInfo,
    statisticSegment: StatisticSegment = WEEK,
    color: CategoryMainColor = CategoryMainColor.Purple,
) {
    IconColumn(
        modifier = Modifier.padding(top = 5.dp),
        icon = R.drawable.ic_accept,
        color = color,
    ) {
        val week = stringResource(id = R.string.statistic_segment_week_completed)
        val month = stringResource(id = R.string.statistic_segment_month_completed)
        val year = stringResource(id = R.string.statistic_segment_year_completed)

        @Composable
        fun getValueBySegment(): String {
            return when (statisticSegment) {
                WEEK -> week
                MONTH -> month
                YEAR -> year
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                CategoryTitle(
                    text = when (statistic) {
                        is StatisticInfo.CompletedStatistic -> stringResource(R.string.completed_statistic)
                        is StatisticInfo.PomodoroStatistic -> stringResource(R.string.pomodoro_statistic)
                    },
                    color = color.accent.pureColor,
                    modifier = Modifier.padding(end = 16.dp)
                )
                AdviceText(
                    text = when (statistic) {
                        is StatisticInfo.CompletedStatistic ->
                            stringResource(
                                id = R.string.description_completed_in_one_s,
                                getValueBySegment()
                            )

                        is StatisticInfo.PomodoroStatistic ->
                            stringResource(
                                id = R.string.pomodoro_statistic_description,
                                getValueBySegment()
                            )
                    },
                    modifier = Modifier.padding(end = 16.dp),
                    color = color.accent.pureColor
                )
            }
            Text(
                text = statistic.info,
                color = color.accent.pureColor,
                style = MaterialTheme.typography.bodyMedium, fontSize = 32.sp
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    DetailedStatisticContent(
        state = DetailedStatisticState(),
        onLoadCalendarDays = { _, _ -> })
}