package com.drowsynomad.mirrovision.presentation.screens.chooseIcon

import androidx.compose.runtime.toMutableStateList
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateViewModel
import com.drowsynomad.mirrovision.presentation.core.common.SideEffect
import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.model.ChooseIconEvent
import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.model.ChooseIconState
import com.drowsynomad.mirrovision.presentation.utils.IStringConverterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Roman Voloshyn (Created on 22.07.2024)
 */

@HiltViewModel
class ChooseIconVM @Inject constructor(
    private val stringConverterManager: IStringConverterManager
): StateViewModel<ChooseIconState, ChooseIconEvent, SideEffect>(
    ChooseIconState(isLoading = true)
) {

    private var previousHabit: HabitIcon? = null
    private var previousCategory: IconsInCategory? = null

    override fun handleUiEvent(uiEvent: ChooseIconEvent) {
        when(uiEvent) {
            ChooseIconEvent.LoadIcons -> loadIcons()
            is ChooseIconEvent.SelectIcon -> selectIcon(uiEvent.icon)
        }
    }

    private fun selectIcon(icon: HabitIcon) {
        uiState.value.icons.forEach {
            it.icons.find { it.isSelected.value }?.isSelected?.value = false
        }

        val category = uiState.value.icons.find { it.title == icon.category }
        val selectedHabitIcon = category?.icons
            ?.find { it.id == icon.id }

        selectedHabitIcon
            ?.isSelected?.value = !icon.isSelected.value

        if(!uiState.value.isSavingEnabled)
            updateState { it.copy(isSavingEnabled = true, selectedIcon = selectedHabitIcon?.icon) }


    /*    previousCategory?.let {
            val newCategory = uiState.value.icons.find { category -> category.id == it.id }
            val newHabit = newCategory?.icons?.find {icon -> icon.id == previousHabit!!.id }

            newHabit?.isSelected?.value = false
        }
*/
        previousCategory = category
    }

    private fun loadIcons() {
        launch(Dispatchers.IO) {
            delay(300)
            val titles = stringConverterManager.getStringArray(R.array.categories)

            val icons = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[0]) }
            val icons1 = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[1]) }
            val icons2 = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[2]) }
            val icons3 = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[3]) }
            val icons4 = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[4]) }
            val icons5 = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[5]) }
            val icons6 = List(16) { HabitIcon(icon = R.drawable.ic_sport_ball, category = titles[6]) }

            val outcomeData = mutableListOf(
                IconsInCategory(titles[0], icons.toMutableStateList()),
                IconsInCategory(titles[1], icons1.toMutableStateList()),
                IconsInCategory(titles[2], icons2.toMutableStateList()),
                IconsInCategory(titles[3], icons3.toMutableStateList()),
                IconsInCategory(titles[4], icons4.toMutableStateList()),
                IconsInCategory(titles[5], icons5.toMutableStateList()),
                IconsInCategory(titles[6], icons6.toMutableStateList())
            )

            withContext(Dispatchers.Main) {
                updateState { it.copy(isLoading = false, icons = outcomeData) }
            }
        }
    }
}