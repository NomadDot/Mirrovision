package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.bounceClick

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Composable
fun HabitIcon(
    modifier: Modifier = Modifier,
    iconSpec: Dp? = null,
    backgroundColor: Color = Color.White,
    accentColor: Color = Color.Gray,
    iconTint: Color = Color.White,
    @DrawableRes icon: Int = R.drawable.ic_add,
    outerRadius: Dp = 12.dp,
    onIconClick: (() -> Unit)? = null
) {
    onIconClick?.let {
        modifier.bounceClick { it.invoke() }
    }

    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = CircleShape)
            .padding(outerRadius),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = accentColor, shape = CircleShape)
        ) {
            val iconModifier =
                if(iconSpec != null) Modifier
                    .width(iconSpec)
                    .height(iconSpec)
                else Modifier.fillMaxSize()

            Icon(
                modifier = iconModifier
                    .background(color = Color.Transparent, shape = CircleShape)
                    .align(Alignment.Center),
                painter = painterResource(id = icon),
                contentDescription = emptyString(),
                tint = iconTint
            )
        }
    }
}

@Composable
fun StateIcon(
    modifier: Modifier = Modifier,
    state: Boolean,
    @DrawableRes onStateTrueIcon: Int,
    @DrawableRes onStateFalseIcon: Int,
    tint: Color
) {
    Icon(
        painter = painterResource(id = if(state) onStateTrueIcon else onStateFalseIcon),
        contentDescription = emptyString(),
        modifier = modifier,
        tint = tint
    )
}

@Composable
fun CategoryChooserIcon(
    modifier: Modifier = Modifier,
    iconSpec: Dp = 40.dp,
    backgroundColor: Color = Color.White,
    accentColor: Color = Color.Gray,
    iconTint: Color = Color.White,
    @DrawableRes icon: Int = R.drawable.ic_add,
    outerRadius: Dp = 5.dp,
    onIconClick: (() -> Unit)? = null
) {
    HabitIcon(
        modifier = modifier,
        backgroundColor = backgroundColor,
        accentColor = accentColor,
        iconSpec = iconSpec,
        iconTint = iconTint,
        icon = icon,
        outerRadius = outerRadius,
        onIconClick = onIconClick
    )
}

@Composable
fun CategoryIcon(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int = R.drawable.ic_sport_ball,
    color: CategoryMainColor = CategoryMainColor.Purple,
    iconSpec: Dp? = null,
    onIconClick: (() -> Unit)? = null
) {
    HabitIcon(
        modifier = modifier
            .width(50.dp)
            .height(50.dp),
        icon = icon,
        iconSpec = iconSpec ?: 35.dp,
        iconTint = color.accent.pureColor,
        accentColor = color.pureColor,
        backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
        outerRadius = 5.dp,
        onIconClick = onIconClick
    )
}

@Preview
@Composable
private fun IconPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HabitIcon(modifier = Modifier
            .width(92.dp)
            .height(92.dp), icon = R.drawable.ic_add)
    }
}