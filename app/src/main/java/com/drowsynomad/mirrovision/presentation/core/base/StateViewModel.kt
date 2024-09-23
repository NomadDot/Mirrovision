package com.drowsynomad.mirrovision.presentation.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.core.common.UiState
import com.voloshynroman.zirkon.presentation.core.common.UiEvent
import kotlinx.coroutines.CoroutineDispatcher
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

abstract class StateViewModel<S: UiState, E: UiEvent, SE: SideEffect>(
    initialState: S,
): ViewModel() {

    protected var sideEffect: SE? = null
    protected val uiState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val publicState: StateFlow<S> by lazy { uiState.asStateFlow() }

    protected fun updateState(stateAction: (S) -> S) {
        uiState.value = stateAction.invoke(uiState.value)
    }

    fun attachSideEffect(sideEffect: SE) { this.sideEffect = sideEffect }

    abstract fun handleUiEvent(uiEvent: E)

    protected fun launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context = dispatcher + SupervisorJob(), block = block)
}