package com.drowsynomad.mirrovision.presentation.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.presentation.core.common.models.DayUI
import com.drowsynomad.mirrovision.presentation.core.common.models.Days
import com.drowsynomad.mirrovision.presentation.core.components.Time

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

fun Modifier.gradient(colors: List<Color>, shape: RoundedCornerShape = RoundedCornerShape(25.dp)): Modifier {
    return this.background(brush = Brush.horizontalGradient(colors = colors), shape = shape)
}

fun Modifier.gradientStroke(colors: List<Color>, width: Dp = 2.dp, shape: RoundedCornerShape = RoundedCornerShape(25.dp)): Modifier {
    return this.border(width, brush = Brush.horizontalGradient(colors = colors), shape = shape)
}

fun Modifier.roundBox(
    color: Color,
    shape: RoundedCornerShape = RoundedCornerShape(25.dp)
): Modifier {
    return this
        .background(color = color, shape = shape)
        .padding(all = 16.dp)
}

enum class ButtonState { Pressed, Idle }
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bounceClick(
    scaledOnPressed: Float = 0.9f,
    enableBound: Boolean = true,
    onLongClick: (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) scaledOnPressed else 1f,
        label = ""
    )

    if(enableBound) {
        this
            .scale(scale = scale)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick?.invoke() },
                onLongClick = { onLongClick?.invoke() }
            )
            .pointerInput(buttonState) {
                awaitPointerEventScope {
                    buttonState = if (buttonState == ButtonState.Pressed) {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    } else {
                        awaitFirstDown(false)
                        ButtonState.Pressed
                    }
                }
            }
    } else this
}

@Composable
fun RowScope.EmptyNavigationBarItem() {
     NavigationBarItem(
        icon = {},
        onClick = {},
        selected = false,
        enabled = false
    )
}

data class FixedInsets(
    val statusBarHeight: Dp = 0.dp,
    val navigationBarHeight: Dp = 0.dp,
)

val LocalFixedInsets = compositionLocalOf<FixedInsets> { error("no FixedInsets provided!") }

@Composable
fun Int.pxToDp(): Dp =  with(LocalDensity.current) { this@pxToDp.toDp() }


fun <T> defaultTween(): TweenSpec<T> = tween(
    durationMillis = 300,
    easing = LinearEasing
)

@Composable
fun VisibilityContainer(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible,
        modifier = modifier,
        enter = fadeIn(animationSpec = defaultTween()) + scaleIn(initialScale = 0.3f, animationSpec = defaultTween()),
        exit = fadeOut(animationSpec = defaultTween()) + scaleOut(animationSpec = defaultTween())
    ) {
        content()
    }
}

@Composable
fun ExpandableContainer(
    isExpanded: Boolean,
    content: @Composable () -> Unit
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = defaultTween()
        ) + fadeIn()
    }

    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = defaultTween()
        ) + fadeOut()
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition,
        exit = exitTransition
    ) {
        content()
    }
}

@Composable
fun AnimatedScene(
    isExpanded: Boolean,
    content: @Composable () -> Unit
) {
    val enterTransition = remember {
      fadeIn(animationSpec = tween(300, easing = LinearOutSlowInEasing))
    }
    AnimatedVisibility(
        visible = isExpanded,
        enter = enterTransition
    ) {
        content()
    }
}

fun fillWeeklyDays(weeklyDayLabels: List<String>, selectedDays: List<Int>? = null): Days {
    val useSelectedDays = selectedDays != null
    return Days(
        days = List(7) {
            val day = it + 1
            DayUI(
                day,
                weeklyDayLabels[it],
                initialSelection =
                    if(useSelectedDays)
                        selectedDays?.contains(day) ?: false
                    else true
            )
        }
    )
}

fun fillMonthlyDays(selectedDays: List<Int>? = null): Days {
    val useSelectedDays = selectedDays != null
    return Days(
        days = List(31) {
          val day = it + 1
            DayUI(
                day,
                day.toString(),
                initialSelection =
                    if(useSelectedDays)
                        selectedDays?.contains(it + 1) ?: false
                    else false
            )
        }
    )
}

fun String.formatTime(): String {
    val hour = this.substringBefore(":")
    val minutes = this.substringAfter(":")
    var formattedTime = ""

    formattedTime = if(hour.length < 2) "0$hour:" else "$hour:"
    formattedTime += if(minutes.length < 2) "0$minutes" else minutes

    return formattedTime
}


fun String.toTime(): Time {
    val hour = this.substringBefore(":")
    val minutes = this.substringAfter(":")

    return Time(hour.toInt(), minutes.toInt())
}