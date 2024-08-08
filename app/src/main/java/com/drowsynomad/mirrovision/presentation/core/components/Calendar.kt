package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.presentation.core.common.models.DayUI
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 01.08.2024)
 */

@Composable
fun MonthlyDays(
    modifier: Modifier = Modifier
) {
    val days = remember {
        List(31) {
            val day = it + 1
            DayUI(dayPosition = day, dayName = day.toString())
        }
    }

    Box(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .roundBox(Color.White)
            .align(Alignment.Center)
        ) {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                columns = GridCells.Fixed(7),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(days, key = { day -> day.dayPosition }) {
                    DayButton(
                        dayButtonState = it,
                        color = CategoryMainColor.Green
                    ) {
                        it.isSelected.value = it.isSelected.value.not()
                    }
                }
            }
        }
    }
}

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
            text = dayButtonState.dayName.toString(),
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            color = if(dayButtonState.isSelected.value) Color.White else color.accent.pureColor
        )
    }
}

@Preview
@Composable
private fun Preview() {
   // MonthlyDays(modifier = Modifier.fillMaxSize())
    MonthlyDaysRows(color = CategoryMainColor.Green, days = emptyList()) {}
}