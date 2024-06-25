package com.drowsynomad.mirrovision.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

fun Modifier.gradient(colors: List<Color>, shape: RoundedCornerShape = RoundedCornerShape(25.dp)): Modifier {
    return this.background(brush = Brush.horizontalGradient(colors = colors), shape = shape)
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