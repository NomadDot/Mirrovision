package com.drowsynomad.mirrovision.presentation.core.base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 15.05.2024)
 */

typealias OnHandleEvent<T> = (T) -> Unit

@Composable
fun <S: UiState, E: UiEvent> StateContent(
    viewModel: StateViewModel<S, E>,
    onBackPressed: (() -> Unit)? = null,
    launchedEffect: (() -> Unit)? = null,
    contentBackground: Color = MaterialTheme.colorScheme.surfaceContainer,
    content: @Composable BoxScope.(uiState: S) -> Unit
) {
    launchedEffect?.let { effect ->
        LaunchedEffect(key1 = Unit) { effect.invoke() }
    }

    BackHandler(
        enabled = onBackPressed != null,
        onBack = { onBackPressed?.invoke() }
    )

    val uiState by viewModel.publicState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = contentBackground)
            .padding(vertical = 20.dp)
    ) {
        content(uiState)
    }
}