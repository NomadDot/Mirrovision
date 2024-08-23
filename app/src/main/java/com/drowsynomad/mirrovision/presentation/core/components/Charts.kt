package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.utils.defaultTween
import com.drowsynomad.mirrovision.presentation.utils.gradient
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 22.08.2024)
 */

private fun getMock(): List<CategoryChartData> =
    listOf(
        CategoryChartData(
            percent = 0.36f,
            categoryColor = CategoryMainColor.Green,
            categoryIcon = R.drawable.ic_sport_ball
        ),
        CategoryChartData(
            percent = 0.42f,
            categoryColor = CategoryMainColor.Purple,
            categoryIcon = R.drawable.ic_sport_ball
        ),
        CategoryChartData(
            percent = 1f,
            categoryColor = CategoryMainColor.Blue,
            categoryIcon = R.drawable.ic_sport_ball
        ),
        CategoryChartData(
            percent = 0.71f,
            categoryColor = CategoryMainColor.Red,
            categoryIcon = R.drawable.ic_sport_ball
        ),
    )

@Composable
fun WeeklyChart(modifier: Modifier = Modifier) {
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
                text = "Progress",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 16.sp
            )
            Text(
                text = "This week",
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
            var percent: Int = 125
            val difference: Int = 25

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(5) {
                    percent -= difference
                    PercentageLine(percent = percent)
                }
            }
            CategoryChart(
                chartData = getMock(),
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
        items(chartData, key = { Random.nextLong() }) {
            IconLine(it, maxLineHeight = maxLineHeight)
        }
    }
}

@Composable
private fun IconLine(
    chartData: CategoryChartData,
    maxLineHeight: Dp
) {
    val showed = remember { mutableStateOf(false) }

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
                    color = chartData.categoryColor.accent.pureColor,
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

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ){
        WeeklyChart(modifier = Modifier.fillMaxWidth())
    }
}