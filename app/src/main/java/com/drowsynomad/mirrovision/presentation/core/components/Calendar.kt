package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Monthly
import com.drowsynomad.mirrovision.presentation.core.components.models.CalendarMode.Weekly
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
    val modifierByState = if(dayCell.isCurrentMonth) modifier
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
                    !dayCell.isCurrentMonth -> color.pureColor
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
fun StatisticCalendar(
    modifier: Modifier = Modifier,
    mode: CalendarMode,
    color: CategoryMainColor
) {
    Column(
        modifier = modifier
    ) {
        WeekLabels(modifier = Modifier.fillMaxWidth(), color = color.accent)
        if(mode is Weekly) {
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                items(mode.days, key = { it.dayPosition }) {
                    StatisticDay(dayCell = it, color = color)
                }
            }
        }
        if(mode is Monthly) {
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

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
   // MonthlyDays(modifier = Modifier.fillMaxSize())
    MonthlyDaysRows(color = CategoryMainColor.Green, days = emptyList()) {}

    Box(modifier = Modifier
        .padding(horizontal = 24.dp)
        .roundBox(color = Color.White)
    ) {
        StatisticCalendar(
            modifier = Modifier.fillMaxWidth(),
            mode = Monthly(
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
            color = CategoryMainColor.Green
        )
    }
}