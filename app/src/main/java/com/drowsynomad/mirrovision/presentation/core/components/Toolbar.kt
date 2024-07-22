package com.drowsynomad.mirrovision.presentation.core.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString

/**
 * @author Roman Voloshyn (Created on 07.07.2024)
 */

@Composable
fun Toolbar(
    modifier: Modifier = Modifier,
    text: String = emptyString(),
    subTitle: String? = null,
    onSettingsClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .height(60.dp)
        .background(color = MaterialTheme.colorScheme.surfaceContainer)
        .padding(horizontal = 24.dp)
        .padding(top = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BigTitle(text = text,
                modifier = Modifier
            )
            subTitle?.let {
                AdviceText(
                    text = it,
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 25.dp)
                )
            }
        }
        @Composable
        fun Icon(@DrawableRes drawable: Int, onClick: () -> Unit) {
            Icon(
                painter = painterResource(id = drawable),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable { onClick.invoke() },
                tint = MaterialTheme.colorScheme.secondary)
        }

        onBackClick?.let { Icon(drawable = R.drawable.ic_back_arrow, it) }
        onSettingsClick?.let { Icon(drawable = R.drawable.ic_back_arrow, it) }
    }
}

@Preview
@Composable
private fun Preview() {
    Toolbar(text = "Preview", onBackClick = {}, subTitle = "A ngdfjkgb dssdaf")
}