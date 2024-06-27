package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.drowsynomad.mirrovision.presentation.theme.PinkCategoryAccent

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Composable
fun BigTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, modifier = modifier, style = MaterialTheme.typography.titleMedium)
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

@Preview(showSystemUi = true)
@Composable
private fun TextPreviews() {
    Column {
        BigTitle("Звичка")
        CategoryTitle(text = "Title", color = PinkCategoryAccent)
    }
}