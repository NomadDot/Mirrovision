package com.drowsynomad.mirrovision.presentation.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.voloshynroman.zirkon.presentation.core.common.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * @author Roman Voloshyn (Created on 15.05.2024)
 */

abstract class StateViewModel<S: UiState, E: UiEvent>(initialState: S): ViewModel() {

    protected val uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val publicState: StateFlow<S> by lazy { uiState.asStateFlow() }

    protected fun updateState(stateAction: (MutableStateFlow<S>) -> Unit) {
        stateAction.invoke(uiState)
    }

    abstract fun handleUiEvent(uiEvent: E)

    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context = Dispatchers.Main + SupervisorJob(), block = block)
}