package com.drowsynomad.mirrovision.presentation.core.components

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Monthly
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Weekly
import com.drowsynomad.mirrovision.presentation.core.components.models.CellProgress
import com.drowsynomad.mirrovision.presentation.core.components.models.DayCell
import com.drowsynomad.mirrovision.presentation.core.components.models.DayUI
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 01.08.2024)
 */

@Composable
fun DayButton(
    modifier: Modifier = Modifier,
    dayButtonState: DayUI,
    color: CategoryMainColor,
    onClick: (DayUI) -> Unit
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .bounceClick { onClick.invoke(dayButtonState) }
            .background(
                color = if (dayButtonState.isSelected.value) color.accent.pureColor else color.pureColor,
                shape = CircleShape
            )
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = dayButtonState.dayName,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = if(dayButtonState.isSelected.value) Color.White else color.accent.pureColor
        )
    }
}


@Composable
fun StatisticDay(
    modifier: Modifier = Modifier,
    dayCell: DayCell,
    color: CategoryMainColor,
) {
    val modifierByState = if(!dayCell.isDayInFuture) modifier
        .size(40.dp)
        .background(
            color = dayCell.calculateShade(color),
            shape = CircleShape
        )
    else
        modifier
            .size(40.dp)
            .border(2.dp, color.pureColor, CircleShape)

    Box(
        modifier = modifierByState
            .padding(vertical = 8.dp, horizontal = 4.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = dayCell.dayPosition,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color =
                when {
                    dayCell.isDayInFuture -> color.pureColor
                    dayCell.progress != CellProgress.NO_ACTIVITY -> Color.White
                    else -> color.accent.pureColor
                }
        )
    }
}

@Composable
fun WeekLabels(
    modifier: Modifier = Modifier,
    color: CategoryAccentColor
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        stringArrayResource(id = R.array.weekly_days)
            .forEach {
                Text(
                    modifier = Modifier.width(40.dp),
                    text = it,
                    fontSize = 10.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = color.pureColor,
                    textAlign = TextAlign.Center
                )
            }
    }
}


@Composable
fun YearProgressCircle(
    modifier: Modifier = Modifier,
    color: CategoryMainColor,
    cell: DayCell
) {
    Spacer(
        modifier = modifier
            .size(6.dp)
            .background(color = cell.calculateShade(color), CircleShape)
    )
}

@Composable
private fun YearlyCells(
    modifier: Modifier = Modifier,
    color: CategoryMainColor,
    cells: List<DayCell>
) {
    val mockedData = List(372) {
        DayCell(dayId = Random.nextLong(), CellProgress.entries.random(), (it + 1).toString(), false)
    }
    
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(31),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(mockedData) {
            YearProgressCircle(
                color = color,
                cell = it
            )
        }
    }
}

@Composable
private fun YearlyStatistic(
    modifier: Modifier,
    color: CategoryMainColor,
    cells: List<DayCell>
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(20.dp)
                .padding(start = 26.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(color.accent.pureColor, RoundedCornerShape(25.dp)),
            )
            Text(
                text = stringResource(id = R.string.statistic_label_days),
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = color.accent.pureColor,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(2.dp)
                    .background(color.accent.pureColor, RoundedCornerShape(25.dp)),
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .height(120.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .width(2.dp)
                    .background(color.accent.pureColor, RoundedCornerShape(25.dp))
            )
            Box(
                modifier = Modifier
                    .rotate(-90f)
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.statistic_label_months),
                    modifier = Modifier
                        .offset(y = (-20).dp),
                    color = color.accent.pureColor,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .width(2.dp)
                    .background(color.accent.pureColor, RoundedCornerShape(25.dp))
            )
        }
    }
    YearlyCells(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, top = 30.dp)
            .height(120.dp),
        cells = cells,
        color = color
    )
}

@Composable
fun StatisticCalendar(
    modifier: Modifier = Modifier,
    mode: CalendarMode,
    color: CategoryMainColor
) {
    if(mode is CalendarMode.Yearly) {
        YearlyStatistic(
            modifier = Modifier,
            color = color,
            cells = listOf()
        )
    } else {
        Column {
            WeekLabels(modifier = Modifier.fillMaxWidth(), color = color.accent)
            if (mode is Weekly) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    items(mode.days) {
                        StatisticDay(dayCell = it, color = color)
                    }
                }
            }
            if (mode is Monthly) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(265.dp)
                        .padding(top = 10.dp),
                    columns = GridCells.Fixed(7),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(mode.days) {
                        StatisticDay(dayCell = it, color = color)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    MonthlyDaysRows(color = CategoryMainColor.Green, days = emptyList()) {}

    Box(modifier = Modifier
        .padding(horizontal = 24.dp)
        .roundBox(color = Color.White)
    ) {
        StatisticCalendar(
            modifier = Modifier.fillMaxWidth(),
            mode = CalendarMode.Yearly("",
                List(42) {
                    val day = Random.nextInt(31)
                    DayCell(
                        dayPosition =
                        "${if(day < 31) (day + 1) else (day - 30).toLong()}",
                        progress = CellProgress.entries.random(),
                        isDayInFuture = day < 31
                    )
                }
            ),
            color = CategoryMainColor.Green
        )
    }
}