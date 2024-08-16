package com.drowsynomad.mirrovision.presentation.screens.dashboard

import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.screens.dashboard.model.DashboardEvent
import com.drowsynomad.mirrovision.presentation.screens.dashboard.model.DashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 17.07.2024)
 */

@HiltViewModel
class DashboardVM @Inject constructor(

): StateViewModel<DashboardState, DashboardEvent, SideEffect>(
    DashboardState()
) {
    override fun handleUiEvent(uiEvent: DashboardEvent) {
        TODO("Not yet implemented")
    }
}