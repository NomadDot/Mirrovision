package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

@Preview(showSystemUi = true)
@Composable
private fun TestPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomNavigationBar()
    }
}

@Composable
fun MainTestScreen(modifier: Modifier = Modifier) {
    TestPreview()
}