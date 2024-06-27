package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Composable
fun HabitIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    accentColor: Color = Color.Gray,
    @DrawableRes icon: Int = 0
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .padding(10.dp),
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .background(color = accentColor, shape = CircleShape)
                .padding(12.dp),
            painter = painterResource(id = icon),
            contentDescription = emptyString()
        )
    }
}

@Preview
@Composable
private fun IconPreview() {
    Column {
        HabitIcon(modifier = Modifier.width(92.dp).height(92.dp), icon = R.drawable.ic_home)
    }
}