package com.drowsynomad.mirrovision.presentation.screens.statistic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.CategoryStatistic
import com.drowsynomad.mirrovision.presentation.core.components.DoubleSelector
import com.drowsynomad.mirrovision.presentation.core.components.HabitStatistic
import com.drowsynomad.mirrovision.presentation.core.components.StatisticCategory
import com.drowsynomad.mirrovision.presentation.core.components.TextSeparator
import com.drowsynomad.mirrovision.presentation.core.components.WeeklyChart
import com.drowsynomad.mirrovision.presentation.core.components.WeeklyProgress
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticState
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor

/**
 * @author Roman Voloshyn (Created on 21.08.2024)
 */

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel) {
    StateContent(viewModel = viewModel) {
        StatisticContent(it)
    }
}

@Composable
private fun StatisticContent(
    state: StatisticState
) {
    Box {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 0.dp)
        ) {
            Spacer(modifier = Modifier.height(5.dp))
            DoubleSelector(
                modifier = Modifier,
                leftButtonLabel = "Weekly statistics",
                rightButtonLabel = "Detailed statistics",
                onLeftButtonClick = {},
                onRightButtonClick = {}
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextSeparator(
                modifier = Modifier,
                text = "Done in a week"
            )

            Spacer(modifier = Modifier.height(15.dp))
            WeeklyProgress(percent = 0.23f)
            Spacer(modifier = Modifier.height(20.dp))
            WeeklyChart()
            Spacer(modifier = Modifier.height(20.dp))

            val mocked = listOf(
                CategoryStatistic(
                    habitStatistics = listOf(
                        HabitStatistic(
                            color = CategoryAccentColor.GreenAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.27f
                        ),
                        HabitStatistic(
                            color = CategoryAccentColor.GreenAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.51f
                        ),
                        HabitStatistic(
                            color = CategoryAccentColor.GreenAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.86f
                        )
                    ),
                    color = CategoryMainColor.Green,
                    percentage = 0.54f
                ),
                CategoryStatistic(
                    habitStatistics = listOf(
                        HabitStatistic(
                            color = CategoryAccentColor.PurpleAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.11f
                        ),
                        HabitStatistic(
                            color = CategoryAccentColor.PurpleAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.52f
                        ),
                        HabitStatistic(
                            color = CategoryAccentColor.PurpleAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.75f
                        ),
                    ),
                    color = CategoryMainColor.Purple,
                    percentage = 0.24f
                ),
                CategoryStatistic(
                    habitStatistics = listOf(
                        HabitStatistic(
                            color = CategoryAccentColor.BlueAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 1f
                        ),
                    ),
                    color = CategoryMainColor.Blue,
                    percentage = 1f
                ),
                CategoryStatistic(
                    habitStatistics = listOf(
                        HabitStatistic(
                            color = CategoryAccentColor.RedAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.4f
                        ),
                        HabitStatistic(
                            color = CategoryAccentColor.RedAccent,
                            icon = R.drawable.ic_sport_ball,
                            percentage = 0.8f
                        )
                    ),
                    color = CategoryMainColor.Red,
                    percentage = 0.62f
                ),
            )

            mocked.forEach {
                StatisticCategory(stat = it)
                Spacer(modifier = Modifier.height(15.dp))
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    StatisticContent(StatisticState())
}
