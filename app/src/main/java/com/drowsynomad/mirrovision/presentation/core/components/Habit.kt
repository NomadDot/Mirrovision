package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.isNegative
import com.drowsynomad.mirrovision.core.isZero
import com.drowsynomad.mirrovision.presentation.core.components.models.Cell
import com.drowsynomad.mirrovision.presentation.core.components.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.components.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.core.components.models.StrokeWidth
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author Roman Voloshyn (Created on 09.07.2024)
 */

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

@Composable
fun HabitAmountStroke(
    modifier: Modifier = Modifier,
    amountStroke: StrokeAmountState,
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
        val animationDuration = remember { 700L }

        val previousPrefilledAmount = remember { mutableIntStateOf(amountStroke.prefilledCellAmount) }
        val difference = amountStroke.prefilledCellAmount - previousPrefilledAmount.intValue

        val inactiveColor = MaterialTheme.colorScheme.surfaceContainer
        val percentAnimation = remember(key1 = difference) {
            if(difference.isZero()) Animatable(1f)
            else Animatable(if (difference.isNegative()) 1f else 0f)
        }
        LaunchedEffect(key1 = difference) {
            launch {
                delay(animationDuration)
                previousPrefilledAmount.intValue = amountStroke.prefilledCellAmount
            }
            if(!difference.isZero())
                percentAnimation.animateTo(
                    targetValue = if (difference.isNegative()) 0f else 1f,
                    animationSpec = tween(durationMillis = animationDuration.toInt(), easing = LinearEasing)
                )
        }
        content()
        Canvas(
            modifier = Modifier
                .size(strokeSize, strokeSize)
                .align(Alignment.Center)
        ) {
            drawIntoCanvas {
                val width = size.width
                val strokeWidth = strWidth.width
                var activeStartAngle = 270f
                var inactiveStartAngle = 270f

                val arcSize = Size(width - strokeWidth, width - strokeWidth)
                val arcOffset = Offset(strokeWidth / 2, strokeWidth / 2)
                val arcStroke = Stroke(strokeWidth, cap = StrokeCap.Round)

                val sweepAngle = 360f / amountStroke.cellAmount

                val defaultGap = 20f
                val gapByAmount = (defaultGap - amountStroke.cellAmount)
                val gap = if (gapByAmount < 11) 11f / 2 else gapByAmount / 2f
                val doubledGap = gap * 2

                val calculatedSweepAngle = (sweepAngle - doubledGap)

                for (cell in 1..amountStroke.cellAmount) {
                    drawArc(
                        color = inactiveColor,
                        startAngle = inactiveStartAngle + gap, sweepAngle = calculatedSweepAngle,
                        useCenter = false, topLeft = arcOffset,
                        size = arcSize, style = arcStroke
                    )

                    inactiveStartAngle += sweepAngle
                }
                if (difference.isNegative())
                    for (cell in 1..amountStroke.prefilledCellAmount) {
                        drawArc(
                            color = amountStroke.filledColor.pureColor,
                            startAngle = activeStartAngle + gap, sweepAngle = calculatedSweepAngle,
                            useCenter = false, topLeft = arcOffset,
                            size = arcSize, style = arcStroke
                        )
                        activeStartAngle += sweepAngle
                    }
                else
                    for (cell in 1..<amountStroke.prefilledCellAmount) {
                        drawArc(
                            color = amountStroke.filledColor.pureColor,
                            startAngle = activeStartAngle + gap, sweepAngle = calculatedSweepAngle,
                            useCenter = false, topLeft = arcOffset,
                            size = arcSize, style = arcStroke
                        )
                        activeStartAngle += sweepAngle
                    }

                if (amountStroke.prefilledCellAmount > 0 || difference.isNegative()) {
                     drawArc(
                        color = amountStroke.filledColor.pureColor,
                        startAngle = activeStartAngle + gap, sweepAngle = calculatedSweepAngle * percentAnimation.value,
                        useCenter = false, topLeft = arcOffset,
                         size = arcSize, style = arcStroke
                    )
                }
            }
        }
    }
}

@Composable
fun AmountHabit(
    modifier: Modifier = Modifier,
    strokeSize: Dp = 80.dp,
    iconSize:Dp = 40.dp,
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
        amountStroke = habitUI.strokeAmount,
        onClick = onClick,
        onLongClick = onLongHabitClick
    ) {
        HabitIcon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            outerRadius = 0.dp,
            accentColor = habitUI.backgroundColor.pureColor,
            iconTint = habitUI.accentColor.pureColor,
            icon = habitUI.icon,
            onIconClick = onClick
        )
    }
}

@Composable
fun StatisticHabit(
    modifier: Modifier = Modifier,
    strokeSize: Dp = 60.dp,
    strWidth: StrokeWidth = StrokeWidth.Custom(16f),
    habitStat: HabitStatistic
) {
    val color = remember { habitStat.color.pureColor }
    val percentage = remember { habitStat.percentage * 100 }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
        ) {
            Icon(
                painter = painterResource(id = habitStat.icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(35.dp)
            )
            Canvas(
                modifier = Modifier
                    .size(strokeSize, strokeSize)
                    .align(Alignment.Center)
            ) {
                drawIntoCanvas {
                    val width = size.width
                    val strokeWidth = strWidth.width
                    val angle = 270f

                    val arcSize = Size(width - strokeWidth, width - strokeWidth)
                    val arcOffset = Offset(strokeWidth / 2, strokeWidth / 2)
                    val arcStroke = Stroke(strokeWidth, cap = StrokeCap.Round)

                    val sweepAngle = 360f

                    val defaultGap = 25f
                    val gapByAmount = (defaultGap - 1)
                    val gap = gapByAmount / 2f
                    val doubledGap = gap * 2

                    val calculatedSweepAngle = (sweepAngle - doubledGap)

                    drawArc(
                        color = Color.White,
                        startAngle = angle + gap, sweepAngle = calculatedSweepAngle,
                        useCenter = false, topLeft = arcOffset,
                        size = arcSize, style = arcStroke
                    )

                    drawArc(
                        color = color,
                        startAngle = angle + gap,
                        sweepAngle = calculatedSweepAngle * habitStat.percentage,
                        useCenter = false, topLeft = arcOffset,
                        size = arcSize, style = arcStroke
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "$percentage".take(
                when {
                    percentage < 10 -> 1
                    percentage < 100 -> 2
                    else -> 3
                }).plus("%"),
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontSize = 14.sp
        )
    }
}

data class Filling(
    val color: CategoryMainColor,
    val cells: List<Cell>
)

data class HabitProgressUI(
    val habitId: Long,
    val name: String,
    val description: String,
    @DrawableRes val icon: Int,
    val color: CategoryMainColor,
    val filling: Filling
)

@Composable
fun ProgressHabit(
    modifier: Modifier = Modifier,
    habitProgressUI: HabitProgressUI,
    onClick: ((Long) -> Unit)? = null
) {
    Box(modifier = modifier
        .bounceClick { onClick?.invoke(habitProgressUI.habitId) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 23.dp, end = 23.dp, top = 10.dp)
                .roundBox(color = habitProgressUI.color.pureColor)
        ) {
            Text(
                text = habitProgressUI.name,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp,
                color = habitProgressUI.color.accent.pureColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = habitProgressUI.description,
                style = MaterialTheme.typography.headlineSmall,
                color = habitProgressUI.color.accent.pureColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Box(modifier = Modifier.fillMaxWidth().roundBox(Color.White)) {
                HabitProgress(
                    modifier = Modifier
                        .align(Alignment.Center),
                    filling = habitProgressUI.filling
                )
            }
        }
        CategoryIcon(
            color = habitProgressUI.color,
            icon = habitProgressUI.icon,
            modifier = Modifier
                .align(TopEnd)
                .padding(end = 14.dp)
                .width(50.dp)
                .height(50.dp)
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
                stroke = StrokeAmountState(
                    cellAmount = 30,
                    prefilledCellAmount = 15,
                    filledColor = CategoryAccentColor.GreenAccent
                )
            )
        )
        Box(modifier = Modifier
            .fillMaxWidth()
            .roundBox(color = CategoryMainColor.Purple.pureColor)
        ) {
            StatisticHabit(
                habitStat = HabitStatistic(
                    color = CategoryAccentColor.PurpleAccent,
                    icon = R.drawable.ic_sport_ball,
                    percentage = 0.04f
                )
            )
        }

        ProgressHabit(
            habitProgressUI = HabitProgressUI(
                habitId = 8210,
                name = "Judy Horn",
                description = "deserunt",
                icon = R.drawable.ic_sport_ball,
                color = CategoryMainColor.Green,
                filling = Filling(cells = listOf(), color = CategoryMainColor.Purple)
            ),
            modifier = Modifier.fillMaxWidth())
    }
}