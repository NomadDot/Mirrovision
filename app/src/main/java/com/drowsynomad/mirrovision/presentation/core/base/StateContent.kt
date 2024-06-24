package com.drowsynomad.mirrovision.presentation.core.base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.voloshynroman.zirkon.presentation.core.common.UiEvent
import com.voloshynroman.zirkon.presentation.core.common.UiState

/**
 * @author Roman Voloshyn (Created on 15.05.2024)
 */

typealias OnHandleEvent<T> = (T) -> Unit

@Composable
fun <S: UiState, E: UiEvent> StateContent(
    viewModel: BaseViewModel<S, E>,
    onBackPressed: (() -> Unit)? = null,
    launchedEffect: (() -> Unit)? = null,
    content: @Composable (uiState: S?) -> Unit
) {
    launchedEffect?.let { effect ->
        LaunchedEffect(key1 = Unit) { effect.invoke() }
    }

    BackHandler(
        enabled = onBackPressed != null,
        onBack = { onBackPressed?.invoke() }
    )

    val uiState by viewModel.uiState

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        content(uiState)
    }
}