package com.drowsynomad.mirrovision.presentation.screens.statistic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.DoubleSelector
import com.drowsynomad.mirrovision.presentation.core.components.TextSeparator
import com.drowsynomad.mirrovision.presentation.core.components.WeeklyChart
import com.drowsynomad.mirrovision.presentation.core.components.WeeklyProgress
import com.drowsynomad.mirrovision.presentation.screens.statistic.model.StatisticState

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
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
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
        WeeklyProgress(percent = 0.53f)
        Spacer(modifier = Modifier.height(20.dp))
        WeeklyChart()
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    StatisticContent(StatisticState())
}
