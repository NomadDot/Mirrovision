package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.getMonthSegmentTitle
import com.drowsynomad.mirrovision.core.getWeekSegmentTitle
import com.drowsynomad.mirrovision.core.getYearSegmentTitle
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Monthly
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Weekly
import com.drowsynomad.mirrovision.presentation.core.components.models.ChartSegment
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.defaultTween
import com.drowsynomad.mirrovision.presentation.utils.gradient
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 22.08.2024)
 */

@Composable
fun WeeklyChart(
    modifier: Modifier = Modifier,
    chartData: List<CategoryChartData> = emptyList()
) {
    Column(
        modifier = modifier
            .gradient(GradientMain)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.label_progress),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp
            )
            Text(
                text = stringResource(R.string.label_this_week),
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .roundBox(color = Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for(percent in 100 downTo 0 step 25) {
                    PercentageLine(percent = percent)
                }
            }
            CategoryChart(
                chartData = chartData,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 36.dp)
                    .offset(y = 6.dp)
            )
        }
    }
}

@Composable
private fun PercentageLine(
    percent: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "${percent}%",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.width(28.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.primary)
    }
}

data class CategoryChartData(
    val id: Int = Random.nextInt(),
    val percent: Float,
    val categoryColor: CategoryMainColor,
    @DrawableRes val categoryIcon: Int
)

@Composable
private fun CategoryChart(
    modifier: Modifier = Modifier,
    chartData: List<CategoryChartData>
) {
    val maxLineHeight = remember { 113.dp }

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        items(chartData, key = { it.id }) {
            IconLine(it, maxLineHeight = maxLineHeight)
        }
    }
}

@Composable
private fun IconLine(
    chartData: CategoryChartData,
    maxLineHeight: Dp
) {
    val showed = rememberSaveable { mutableStateOf(false) }

    val percent = animateFloatAsState(
        targetValue = if(showed.value) chartData.percent else 0f,
        label = "widthProgress",
        animationSpec = defaultTween(600)
    )

    LaunchedEffect(key1 = Unit) { showed.value = true }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(maxLineHeight * percent.value)
                .width(14.dp)
                .background(
                    color = chartData.categoryColor.pureColor,
                    RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                )
        )
        Icon(
            painter = painterResource(id = chartData.categoryIcon),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = chartData.categoryColor.accent.pureColor
        )
    }
}


@Composable
fun ChartSelector(
    modifier: Modifier = Modifier,
    color: CategoryMainColor,
    onSegmentChanged: (ChartSegment) -> Unit
) {
    val (getSegment, setSegment) = remember {
        mutableStateOf(ChartSegment.MONTH)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        val defaultModifier = remember {
            Modifier
                .bounceClick()
                .weight(1f)
        }

        val unselectedModifier = remember {
            defaultModifier
                .border(
                    width = 2.dp,
                    color = color.accent.pureColor,
                    shape = RoundedCornerShape(25.dp)
                )
                .height(40.dp)
        }

        val activeModifier = remember {
            defaultModifier
                .background(
                    color = color.accent.pureColor,
                    shape = RoundedCornerShape(25.dp)
                )
                .height(40.dp)
        }

        ChartSegment.entries.forEach {
            DefaultButton(
                text = stringResource(it.stringResource),
                isButtonSelected = getSegment == it,
                color = color,
                modifier = if(getSegment == it) activeModifier else unselectedModifier
            ) {
                if(getSegment != it) {
                    setSegment.invoke(it)
                    onSegmentChanged.invoke(it)
                }
            }
        }
    }
}

@Composable
fun StatisticSegmentChart(
    modifier: Modifier = Modifier,
    segment: ChartSegment,
    weeklyData: Weekly,
    monthlyData: Monthly,
    color: CategoryMainColor
) {
    val weeklyDaysSegment = remember { getWeekSegmentTitle() }
    val monthlySegment = remember { getMonthSegmentTitle() }
    val yearlySegment = remember { getYearSegmentTitle() }

    IconColumn(
        icon = R.drawable.ic_calendar,
        color = color
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 34.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                icon = R.drawable.ic_back_arrow,
                tint = color.accent.pureColor
            ) {

            }
            CategoryTitle(
                text = when(segment) {
                    ChartSegment.WEEK -> weeklyDaysSegment
                    ChartSegment.MONTH -> monthlySegment
                    ChartSegment.YEAR -> yearlySegment
                },
                color = color.accent.pureColor
            )
            IconButton(
                icon = R.drawable.ic_arrow_next,
                tint = color.accent.pureColor
            ) {

            }
        }
        Box(modifier = Modifier
            .padding(top = 16.dp)
            .roundBox(color = Color.White)
        ) {
            StatisticCalendar(
                mode = if(segment == ChartSegment.WEEK) weeklyData else monthlyData,
                color = color
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ){
        WeeklyChart(modifier = Modifier.fillMaxWidth())
        ChartSelector(color = CategoryMainColor.Green) {}
//        StatisticSegmentChart(segment = ChartSegment.WEEK, color = CategoryMainColor.Green)
    }
}