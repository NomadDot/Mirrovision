package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.StrokeAmount
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary
import com.drowsynomad.mirrovision.presentation.utils.bounceClick

/**
 * @author Roman Voloshyn (Created on 09.07.2024)
 */

@Composable
fun HabitItem(
    habit: HabitUI,
    categoryAssets: CategoryAssets,
    modifier: Modifier = Modifier,
    onCreateHabit: (CategoryAssets) -> Unit,
) {
    Box(modifier = modifier) {
        HabitIcon(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            iconSpec = if(habit.isDefaultIcon) 16.dp else 30.dp,
            accentColor = habit.backgroundColor.pureColor,
            iconTint = habit.accentColor.pureColor,
            icon = R.drawable.ic_add,
            outerRadius = 5.dp
        ) {
            onCreateHabit.invoke(categoryAssets)
        }
    }
}

@Composable
fun HabitCounter(
    modifier: Modifier = Modifier,
    defaultValue: Int = 1,
    styleColor: Color = LightPrimary,
    accentColor: Color,
    onCountChanged: (Int) -> Unit
) {
    val countValue = remember {
        mutableIntStateOf(defaultValue)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PrimaryButton(
            icon = R.drawable.ic_minus,
            isEnabled = countValue.intValue > 1,
            containerColor = styleColor,
            modifier = Modifier.width(width = 100.dp),
        ) {
            countValue.intValue -= 1
            onCountChanged.invoke(countValue.intValue)
        }
        CounterText(
            countValue.intValue.toString(),
            modifier = Modifier.weight(2f),
            color = accentColor
        )
        PrimaryButton(
            modifier = Modifier.width(width = 100.dp),
            isEnabled = countValue.intValue < 30,
            icon = R.drawable.ic_add,
            containerColor = styleColor,
        ) {
            countValue.intValue += 1
            onCountChanged.invoke(countValue.intValue)
        }
    }
}

sealed class StrokeWidth(val width: Float = 25f) {
    data object Default: StrokeWidth()
    data class Custom(val customWidth: Float): StrokeWidth(customWidth)
}

@Composable
fun HabitAmountStroke(
    modifier: Modifier = Modifier,
    amountStroke: StrokeAmount,
    strWidth: StrokeWidth = StrokeWidth.Default,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    strokeSize: Dp = 50.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .bounceClick(onClick = onClick, onLongClick = onLongClick)
    ) {
        val inactiveColor = MaterialTheme.colorScheme.surfaceContainer
        content()
        Canvas(
            modifier = Modifier
                .size(strokeSize, strokeSize)
                .align(Alignment.Center)
        ) {
            drawIntoCanvas {
                val width = size.width
                val strokeWidth = strWidth.width
                var startAngle = 270f

                val sweepAngle = 360f / amountStroke.cellAmount

                val gapForFirst = 20f
                for (cell in 1..amountStroke.cellAmount) {
                    val gapFirst = (gapForFirst - amountStroke.cellAmount)
                    val gap = if(gapFirst < 11) 11f / 2 else gapFirst/2f

                    drawArc(
                        color =
                            if (cell <= amountStroke.prefilledCellAmount) amountStroke.filledColor.pureColor
                            else inactiveColor,
                        startAngle = startAngle + gap,
                        sweepAngle = sweepAngle - gap * 2,
                        useCenter = false,
                        topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                        size = Size(width - strokeWidth, width - strokeWidth),
                        style = Stroke(strokeWidth, cap = StrokeCap.Round)
                    )

                    startAngle += sweepAngle
                }
            }
        }
    }
}

@Composable
fun AmountHabit(
    modifier: Modifier = Modifier,
    strokeSize: Dp = 50.dp,
    iconSize:Dp = 35.dp,
    strokeWidth: StrokeWidth = StrokeWidth.Default,
    habitUI: HabitUI = HabitUI(),
    onLongHabitClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    HabitAmountStroke(
        modifier = modifier
            .size(strokeSize),
        strokeSize = strokeSize,
        strWidth = strokeWidth,
        amountStroke = habitUI.strokeState.value,
        onClick = onClick,
        onLongClick = onLongHabitClick
    ) {
        HabitIcon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
//            iconSpec = iconSize,
            outerRadius = 0.dp,
            accentColor = habitUI.backgroundColor.pureColor,
            iconTint = habitUI.accentColor.pureColor,
            icon = habitUI.icon,
            onIconClick = onClick
        )
    }
}

@Preview(showSystemUi = true, device = Devices.DEFAULT)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AmountHabit(
            iconSize = 50.dp,
            strokeSize = 60.dp,
            strokeWidth = StrokeWidth.Custom(14f),
            habitUI = HabitUI(
                id = 6916,
                name = "Jackie Wilson",
                description = "detracto",
                icon = R.drawable.ic_sport_ball,
                backgroundColor = CategoryMainColor.Green,
                attachedCategoryId = 1409,
                stroke = StrokeAmount(cellAmount = 30, prefilledCellAmount = 15, filledColor = CategoryAccentColor.GreenAccent)
            ))
    }
}