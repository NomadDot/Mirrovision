package com.drowsynomad.mirrovision.presentation.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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
fun Modifier.bounceClick(scaledOnPressed: Float = 0.8f, enableBound: Boolean = true, onClick: (() -> Unit)? = null) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(
        if (buttonState == ButtonState.Pressed) scaledOnPressed else 1f,
        label = ""
    )

    if(enableBound) {
        this
            .scale(scale = scale)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick?.invoke() }
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