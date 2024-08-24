package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.presentation.theme.GradientButtonColors
import com.drowsynomad.mirrovision.presentation.theme.ProgressBackground
import com.drowsynomad.mirrovision.presentation.utils.defaultTween
import kotlin.time.measureTime

/**
 * @author Roman Voloshyn (Created on 20.07.2024)
 */

@Composable
fun DefaultProgress(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun WeeklyProgress(
    percent: Float,
    modifier: Modifier = Modifier
) {
    val showed = rememberSaveable {
        mutableStateOf(false)
    }

    val canvasWidth = remember {
        mutableFloatStateOf(0f)
    }

    val widthProgress = animateFloatAsState(
        targetValue = if(showed.value) percent else 0f,
        label = "widthProgress",
        animationSpec = defaultTween(600)
    )

    val alphaProgress = animateFloatAsState(
        targetValue = if(showed.value) 1f else 0f,
        label = "alphaProgress",
        animationSpec = defaultTween(600)
    )
    
    LaunchedEffect(key1 = showed.value) {
        showed.value = true
    }

    val primaryColor = MaterialTheme.colorScheme.primary
    val brush = Brush.horizontalGradient(GradientButtonColors)
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = ProgressBackground, RoundedCornerShape(25.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(25.dp)
                )
        )
        val font = MaterialTheme.typography.titleSmall

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if(canvasWidth.floatValue == 0f)
                canvasWidth.floatValue = this.size.width

            drawRoundRect(
                brush = brush,
                style = Fill,
                alpha = alphaProgress.value,
                size = Size(this.size.width * widthProgress.value, 50.dp.toPx()),
                cornerRadius = CornerRadius(25.dp.toPx(), 25.dp.toPx())
            )
            drawText(
                textMeasurer,
                "${widthProgress.value * 100f}".take(2).plus("%"),
                topLeft = Offset(if(percent > 0.15f) -(16f).dp.toPx() else (32f).dp.toPx(), 15.dp.toPx()),
                style = TextStyle(
                    fontStyle = font.fontStyle,
                    fontFamily = font.fontFamily,
                    textAlign = TextAlign.End,
                    color = if(percent > 0.15f) Color.White else primaryColor),
                size = Size((this.size.width * widthProgress.value), 50.dp.toPx())
            )
        }
     /*   Box(
            modifier = Modifier
                .width((canvasWidth.floatValue * widthProgress.value).dp)
        ) {
            Text(
                text = ,
                modifier = Modifier
                    .wrapContentWidth()
                    .height(50.dp)
                    .align(Alignment.CenterEnd),
                fontSize = 20.sp,
                textAlign = TextAlign.End,
                color = Color.Black
            )
        }*/
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column {
        WeeklyProgress(percent = 0.7f)
    }
    
}