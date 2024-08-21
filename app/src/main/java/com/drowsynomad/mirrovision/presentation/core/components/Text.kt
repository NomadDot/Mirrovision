package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary
import com.drowsynomad.mirrovision.presentation.theme.LightPinkCategoryAccent

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Composable
fun BigTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
fun BigTitle(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium,
        color = color
    )
}

@Composable
fun CategoryTitle(
    text: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        color = color,
        text = text,
        style = MaterialTheme.typography.titleSmall,
    )
}

@Composable
fun AdviceText(
    text: String,
    modifier: Modifier,
    color: Color = LightPrimary
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
fun CounterText(
    value: String = emptyString(),
    modifier: Modifier = Modifier,
    color: Color
) {
    Text(
        modifier = modifier,
        text = value,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 32.sp,
        color = color
    )
}

@Composable
fun ExplainTitle(
    modifier: Modifier = Modifier,
    title: String,
    explain: String,
    titleColor: Color = MaterialTheme.colorScheme.primary,
    explainColor: Color = MaterialTheme.colorScheme.primary
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BigTitle(text = title, color = titleColor)
        AdviceText(text = explain, modifier = Modifier, color = explainColor)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TextPreviews() {
    Column {
        BigTitle("Звичка")
        CategoryTitle(text = "Title", color = LightPinkCategoryAccent)
        CounterText("12", color = LightPrimary)
        ExplainTitle(title = "Кількість", explain = "(разів на день)", titleColor = LightPrimary)
    }
}