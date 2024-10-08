package com.drowsynomad.mirrovision.presentation.core.components.colorPicker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.CategoryColors
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 26.06.2024)
 */

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    colors: List<ColorShades>,
    colorsAlignment: Alignment = Alignment.CenterStart,
    onColorClick: ((ColorShades) -> Unit)? = null
) {
    Box(
        modifier = modifier
            .roundBox(Color.White),
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.align(colorsAlignment)
        ) {
            items(colors, key = { colorShade -> colorShade.id }) { colorShade ->
                CategoryColorItem(colorShade = colorShade) {
                    onColorClick?.invoke(it)
                }
            }
        }
    }
}

@Composable
private fun CategoryColorItem(
    modifier: Modifier = Modifier,
    colorShade: ColorShades,
    onColorClick: ((ColorShades) -> Unit)? = null
) {
    Box(modifier = modifier
        .bounceClick {
            onColorClick?.invoke(colorShade)
        }
    ) {
        Canvas(
            modifier = Modifier
                .size(30.dp)
                .clipToBounds()
        ) {
            drawCircle(colorShade.main.pureColor)
        }
        AnimatedVisibility(
            visible = colorShade.selected,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_checkmark),
                contentDescription = emptyString(),
                tint = colorShade.accent.pureColor,
            )
        }
    }
}

@Preview
@Composable
private fun ColorPickerPreview() {
    ColorPicker(
        colors = CategoryColors.map { color -> ColorShades(color, color.accent) }
    )
}