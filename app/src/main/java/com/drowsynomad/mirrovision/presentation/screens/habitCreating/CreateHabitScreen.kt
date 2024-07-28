package com.drowsynomad.mirrovision.presentation.screens.habitCreating

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitNavigationModel
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.StrokeAmountState
import com.drowsynomad.mirrovision.presentation.core.components.AddingButton
import com.drowsynomad.mirrovision.presentation.core.components.AmountHabit
import com.drowsynomad.mirrovision.presentation.core.components.BackButton
import com.drowsynomad.mirrovision.presentation.core.components.BigTitle
import com.drowsynomad.mirrovision.presentation.core.components.CancelableAndSaveableButton
import com.drowsynomad.mirrovision.presentation.core.components.ExplainTitle
import com.drowsynomad.mirrovision.presentation.core.components.HabitCounter
import com.drowsynomad.mirrovision.presentation.core.components.InputField
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitEvent
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.LocalFixedInsets

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */

@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitVM,
    habitUI: HabitNavigationModel,
    onChooseIconNavigation: (CategoryMainColor) -> Unit,
    onBackNavigation: Navigation,
    onSaveHabitNavigation: (HabitUI) -> Unit
) {
    StateContent(
        useStatusBarPadding = false,
        viewModel = viewModel,
        launchedEffect = {
            viewModel.handleUiEvent(CreateHabitEvent.ConfigureStateForHabit(habitUI.toHabitUI())) }
    ) {
        CreateHabitContent(
            it,
            onBackNavigation,
            onSaveHabit = {
                if(!habitUI.isForIntro)
                    viewModel.handleUiEvent(CreateHabitEvent.SaveHabitDirectlyToStorage(it))
                onSaveHabitNavigation.invoke(it)
            },
            onChooseIconNavigation
        )
    }
}

@Composable
fun CreateHabitContent(
    state: CreateHabitState,
    onBackNavigation: Navigation,
    onSaveHabit: (HabitUI) -> Unit,
    onChooseIconNavigation: (CategoryMainColor) -> Unit
) {
    val habitUI = state.habitUI
    habitUI?.let {
        val accentColor = habitUI.accentColor.pureColor

        val icon = mutableIntStateOf(habitUI.icon)

        val habitName = rememberSaveable { mutableStateOf(habitUI.name) }
        val habitDescription = rememberSaveable { mutableStateOf(habitUI.description) }
        val habitCountPerDay = rememberSaveable { mutableIntStateOf(habitUI.stroke.cellAmount) }
        val selectedHabits = rememberSaveable { mutableIntStateOf(0) }
        val isSavingEnabled = rememberSaveable { mutableStateOf(false) }

        fun checkIfSavingButtonEnabled() {
            isSavingEnabled.value = icon.intValue != R.drawable.ic_add &&
                    habitName.value.isNotEmpty() && habitDescription.value.isNotEmpty()
        }

        checkIfSavingButtonEnabled()

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CircleIcon(
                    icon = habitUI.icon,
                    color = habitUI.backgroundColor,
                    count = habitCountPerDay.intValue,
                    selected = selectedHabits.intValue
                ) {
                    onChooseIconNavigation.invoke(habitUI.backgroundColor)
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    BigTitle(text = stringResource(R.string.label_habit_name), color = accentColor)
                    InputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        color = accentColor,
                        elevation = 20.dp,
                        maxLimit = 30,
                        isSingleLine = true,
                        prefilledValue = habitName.value,
                        hint = stringResource(R.string.label_habit_enter_the_name)
                    ) {
                        habitName.value = it
                        checkIfSavingButtonEnabled()
                    }
                    BigTitle(
                        text = stringResource(R.string.label_description),
                        modifier = Modifier.padding(top = 20.dp),
                        color = accentColor
                    )
                    InputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        color = accentColor,
                        elevation = 20.dp,
                        maxLimit = 100,
                        hint = stringResource(R.string.label_add_habit_description),
                        prefilledValue = habitDescription.value
                    ) {
                        habitDescription.value = it
                        checkIfSavingButtonEnabled()
                    }
                    ExplainTitle(
                        title = stringResource(R.string.label_amount),
                        explain = stringResource(R.string.label_per_day),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        titleColor = accentColor,
                        explainColor = accentColor
                    )
                    HabitCounter(
                        modifier = Modifier.padding(top = 15.dp),
                        defaultValue = habitCountPerDay.intValue,
                        styleColor = habitUI.backgroundColor.pureColor,
                        accentColor = accentColor
                    ) {
                        habitCountPerDay.intValue = it
                        checkIfSavingButtonEnabled()
                    }
                    BigTitle(
                        text = stringResource(R.string.label_regularity),
                        modifier = Modifier.padding(top = 20.dp),
                        color = accentColor
                    )
                    AddingButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        color = accentColor
                    ) {

                    }
                }
            }
            CancelableAndSaveableButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                cancelButtonLabel = R.string.button_cancel,
                primaryButtonLabel = R.string.button_save,
                containerColor = accentColor,
                onCancelButtonClick = onBackNavigation,
                isPrimaryButtonEnabled = isSavingEnabled.value,
                onPrimaryButtonClick = {
                    onSaveHabit.invoke(
                        habitUI.copy(
                            name = habitName.value,
                            description = habitDescription.value,
                            stroke = habitUI.stroke.copy(cellAmount = habitCountPerDay.intValue),
                            icon = icon.intValue
                        )
                    )
                }
            )
            BackButton(
                modifier = Modifier
                    .padding(top = 46.dp)
                    .padding(start = 24.dp),
                color = accentColor,
                onClick = onBackNavigation
            )
        }
    }
}

@Composable
fun CircleIcon(
    icon: Int,
    color: CategoryMainColor,
    modifier: Modifier = Modifier,
    count: Int = 1,
    selected: Int = 0,
    onClick: () -> Unit
) {
    Box(modifier = Modifier) {
        Canvas(modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clipToBounds()
        ) {
            drawCircle(
                color = color.pureColor,
                radius = size.width/1.8f,
                center = Offset(this.center.x, y = 0f)
            )
        }
        AmountHabit(
            habitUI = HabitUI(
                icon = icon,
                backgroundColor = color,
                stroke = StrokeAmountState(count, selected, color.accent)
            ),
            modifier = Modifier
                .padding(top = 60.dp + LocalFixedInsets.current.statusBarHeight)
                .size(115.dp)
                .align(Alignment.TopCenter),
            strokeSize = 115.dp,
            iconSize = 60.dp,
            onClick = onClick
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CreateHabitContent(state = CreateHabitState(HabitUI()), {}, {}, {})
    }
}