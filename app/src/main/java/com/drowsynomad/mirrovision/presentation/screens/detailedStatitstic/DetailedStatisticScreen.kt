package com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.AdviceText
import com.drowsynomad.mirrovision.presentation.core.components.CategoryTitle
import com.drowsynomad.mirrovision.presentation.core.components.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.ChartSelector
import com.drowsynomad.mirrovision.presentation.core.components.DefaultProgress
import com.drowsynomad.mirrovision.presentation.core.components.IconColumn
import com.drowsynomad.mirrovision.presentation.core.components.StatisticSegmentChart
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Monthly
import com.drowsynomad.mirrovision.presentation.core.components.models.ChartSegment
import com.drowsynomad.mirrovision.presentation.core.components.models.DayCell
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticEvent
import com.drowsynomad.mirrovision.presentation.screens.detailedStatitstic.model.DetailedStatisticState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 13.09.2024)
 */

@Composable
fun DetailedStatisticScreen(
    viewModel: DetailedStatisticVM,
    habitId: Long
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = {
            viewModel.handleUiEvent(DetailedStatisticEvent.LoadStatistic(habitId))
        }
    ) {
        if(it.isLoading)
            DefaultProgress()
        else
            DetailedStatisticContent(it)
    }
}

@Composable
private fun DetailedStatisticContent(
    state: DetailedStatisticState
) {
    val segment = rememberSaveable {
        mutableStateOf(ChartSegment.MONTH)
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.offset(y = (-15).dp)
    ) {
        NameStatistic(
            habitName = state.habitName,
            habitDescription = state.habitDescription,
            color = state.color,
            habitIcon = state.habitIcon
        )
        ChartSelector(
            color = state.color,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) { segment.value = it }
        StatisticSegmentChart(
            segment = segment.value,
            monthlyData = Monthly(
                List(42) {
                    val day = Random.nextInt(31)
                    DayCell(
                        dayPosition =
                        "${if(day < 31) (day + 1) else (day - 30).toLong()}",
                        progress = CellProgress.entries.random(),
                        isCurrentMonth = day < 31
                    )
                }
            ),
            weeklyData = CalendarMode.Weekly(
                List(7) {
                    DayCell(
                        dayPosition = it.toString(),
                        progress = CellProgress.entries.random(),
                        isCurrentMonth = true
                    )
                }
            ),
            color = state.color
        )
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
            text = habitDescription ,
            modifier = Modifier.padding(top = 5.dp, end = 16.dp),
            color = color.accent.pureColor
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    DetailedStatisticContent(state = DetailedStatisticState())
}