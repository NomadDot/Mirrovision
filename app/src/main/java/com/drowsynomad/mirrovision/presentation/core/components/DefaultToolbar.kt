package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.LightMainBackground

/**
 * @author Roman Voloshyn (Created on 07.07.2024)
 */

@Composable
private fun ToolbarContent(
    modifier: Modifier = Modifier,
    title: String = emptyString(),
    accentColor: Color,
    backgroundColor: Color,
    subTitle: String? = null,
    onSettingsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    val contentColor = animateColorAsState(
        accentColor,
        animationSpec = tween(500),
        label = emptyString()
    )

    Box(modifier = modifier
        .fillMaxWidth()
        .height(60.dp)
        .background(color = backgroundColor)
        .padding(horizontal = 24.dp)
        .padding(top = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigTitle(
                text = title,
                color = contentColor.value,
                modifier = Modifier,
            )
            subTitle?.let {
                AdviceText(
                    text = it,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 25.dp),
                )
            }
        }
        @Composable
        fun Icon(@DrawableRes drawable: Int, onClick: () -> Unit) {
            androidx.compose.material3.Icon(
                painter = painterResource(id = drawable),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onClick.invoke() },
                tint = accentColor
            )
        }

        onBackClick?.let { Icon(drawable = R.drawable.ic_back_arrow, it) }
        onSettingsClick?.let { Icon(drawable = R.drawable.ic_settings, it) }
    }
}

@Composable
fun DefaultToolbar(
    modifier: Modifier = Modifier,
    text: String = emptyString(),
    subTitle: String? = null,
    customColor: CategoryAccentColor? = null,
    onSettingsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    ToolbarContent(
        modifier = modifier,
        title = text,
        accentColor = customColor?.pureColor ?: MaterialTheme.colorScheme.secondary,
        backgroundColor = LightMainBackground,
        subTitle = subTitle,
        onSettingsClick = onSettingsClick,
        onBackClick = onBackClick
    )
}

@Composable
fun CustomizedToolbar(
    modifier: Modifier = Modifier,
    title: String = emptyString(),
    accentColor: Color,
    backgroundColor: Color,
    subTitle: String? = null,
    onSettingsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    ToolbarContent(
        modifier = modifier,
        title = title,
        accentColor = accentColor,
        backgroundColor = backgroundColor,
        subTitle = subTitle,
        onSettingsClick = onSettingsClick,
        onBackClick = onBackClick
    )
}

@Preview
@Composable
private fun Preview() {
    DefaultToolbar(text = "Preview", onBackClick = {}, subTitle = "A ngdfjkgb dssdaf")
}