package com.drowsynomad.mirrovision.presentation.screens.chooseIcon.model

import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.HabitIcon
import com.voloshynroman.zirkon.presentation.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 22.07.2024)
 */

sealed class ChooseIconEvent: UiEvent {
    data object LoadIcons: ChooseIconEvent()
    data class SelectIcon(val icon: HabitIcon): ChooseIconEvent()
}