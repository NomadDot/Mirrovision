package com.drowsynomad.mirrovision.presentation.core.base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.drowsynomad.mirrovision.presentation.utils.LocalFixedInsets
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 15.05.2024)
 */

typealias OnHandleEvent<T> = (T) -> Unit

@Composable
fun <S: UiState, E: UiEvent, SE: SideEffect> StateContent(
    viewModel: StateViewModel<S, E, SE>,
    sideEffect: SE? = null,
    onBackPressed: (() -> Unit)? = null,
    launchedEffect: (() -> Unit)? = null,
    isStatusBarPadding: Boolean = true,
    contentBackground: Color = MaterialTheme.colorScheme.surfaceContainer,
    content: @Composable BoxScope.(uiState: S) -> Unit
) {
    launchedEffect?.let { effect ->
        LaunchedEffect(key1 = Unit) {
            effect.invoke()
        }
    }

    sideEffect?.let { viewModel.attachSideEffect(it) }

    BackHandler(
        enabled = onBackPressed != null,
        onBack = { onBackPressed?.invoke() }
    )

    val uiState by viewModel.publicState.collectAsStateWithLifecycle()

    val contentModifier = Modifier
        .fillMaxSize()
        .background(color = contentBackground)
        .windowInsetsPadding(WindowInsets.Companion.navigationBars)
        .padding(top = if(isStatusBarPadding) 20.dp + LocalFixedInsets.current.statusBarHeight else 0.dp)

    Box(
        modifier = contentModifier.imePadding()
    ) {
        content(uiState)
    }
}