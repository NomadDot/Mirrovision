package com.drowsynomad.mirrovision.presentation.core.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.domain.models.RegularityType
import com.drowsynomad.mirrovision.presentation.core.components.models.DayUI
import com.drowsynomad.mirrovision.presentation.core.components.models.RegularityContentUI
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.RegularityActions
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.LightMainBackground
import com.drowsynomad.mirrovision.presentation.theme.ShadowColor
import com.drowsynomad.mirrovision.presentation.theme.accent
import com.drowsynomad.mirrovision.presentation.theme.pureColor
import com.drowsynomad.mirrovision.presentation.utils.ExpandableContainer
import com.drowsynomad.mirrovision.presentation.utils.VisibilityContainer
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import com.drowsynomad.mirrovision.presentation.utils.toTime

/**
 * @author Roman Voloshyn (Created on 01.08.2024)
 */

@Composable
fun RegularityColumn(
    modifier: Modifier = Modifier,
    color: CategoryMainColor = CategoryMainColor.Green,
    regularityContentUI: SnapshotStateList<RegularityContentUI>,
    regularityActions: RegularityActions
) {
    val context = LocalContext.current

    if(regularityContentUI.isEmpty())
        regularityActions.onAddNewRegularity?.invoke(false)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        regularityContentUI.forEach {
            RegularityPickerItem(
                state = it,
                color = color,
                cancellable = it.cancellable,
                regularityActions = regularityActions
            )
        }
        AddingButton(
            modifier = Modifier
                .fillMaxWidth(),
            color = color.accent.pureColor,
        ) {
            regularityActions.onAddNewRegularity?.invoke(true)
        }
    }
}

@Composable
private fun RegularityPickerItem(
    modifier: Modifier = Modifier,
    state: RegularityContentUI,
    color: CategoryMainColor = CategoryMainColor.Green,
    cancellable: Boolean = false,
    regularityActions: RegularityActions
) {
    val showTimeSelector = rememberSaveable { mutableStateOf(false) }

    if(showTimeSelector.value)
        TimePickerDialog(
            presetTime = state.stateTime.value.toTime(),
            color = color,
            onConfirm = {
                showTimeSelector.value = false
                regularityActions.onTimeChanged
                    ?.invoke(it, true, state.id)
            },
            onDismiss =  { showTimeSelector.value = false }
        )
    
    Box(modifier = modifier
        .fillMaxWidth()
        .shadow(20.dp, shape = RoundedCornerShape(25.dp), spotColor = ShadowColor)
        .roundBox(color = Color.White)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    VisibilityContainer(visible = state.stateUseTime.value) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.bounceClick { showTimeSelector.value = true },
                        ) {
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = emptyString(),
                                tint = color.accent.pureColor,
                            )
                            Text(
                                text = state.stateTime.value,
                                style = MaterialTheme.typography.bodyMedium,
                                color = color.accent.pureColor,
                                fontSize = 32.sp
                            )
                        }
                    }
                    VisibilityContainer(visible = !state.stateUseTime.value) {
                        Text(
                            text = stringResource(R.string.label_days),
                            style = MaterialTheme.typography.bodyMedium,
                            color = color.accent.pureColor,
                            fontSize = 32.sp
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text =
                             stringResource(
                                 if(state.stateType.value is RegularityType.WeeklyType) R.string.label_weekly_brackets
                                 else  R.string.label_monthly_brackets),
                        style = MaterialTheme.typography.headlineSmall,
                        color = color.accent.pureColor
                    )
                    if(cancellable)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = emptyString(),
                            tint = color.accent.pureColor,
                            modifier = Modifier
                                .clickable {
                                    regularityActions.onRemoveRegularity
                                        ?.invoke(state.id)
                                }
                        )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LabelWithCheckbox(
                modifier = Modifier.fillMaxWidth(),
                color = color.accent.pureColor,
                isSelected =  state.stateUseTime.value,
                label = stringResource(R.string.i_want_to_use_time_reminder)
            ) {
                regularityActions.onTimeChanged
                    ?.invoke(null, !state.stateUseTime.value, state.id)
            }
            Spacer(modifier = Modifier.height(16.dp))
            RegularityTypeBox(
                type = state.stateType.value,
                weekDays = state.week.days,
                monthlyDays = state.month.days,
                color = color
            ) { dayState ->
                regularityActions.onDaysSelected?.invoke(dayState, state.id)
            }
            RegularityExpanderIcon(
                modifier = Modifier.padding(top = 16.dp),
                isCollapsed = state.stateType.value is RegularityType.WeeklyType,
                color = color.accent.pureColor,
                label = stringResource(
                    if(state.stateType.value is RegularityType.WeeklyType) R.string.label_monthly_brackets
                    else  R.string.label_weekly_brackets
                ),
            ) {
                regularityActions.onTypeChanged
                    ?.invoke(state.stateType.value.invert(), state.id)
            }
        }
    }
}

@Composable
fun RegularityExpanderIcon(
    modifier: Modifier = Modifier,
    isCollapsed: Boolean,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onClick.invoke() },
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        StateIcon(
            modifier = Modifier,
            state = isCollapsed,
            onStateTrueIcon = R.drawable.ic_arrow_down,
            onStateFalseIcon = R.drawable.ic_arrow_up,
            tint = color)
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            color = color)
    }
}

@Composable
fun LabelWithCheckbox(
    modifier: Modifier = Modifier,
    label: String,
    color: Color,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = color,
            fontSize = 12.sp
        )
        Box(modifier = Modifier
            .size(24.dp)
            .bounceClick { onClick() }
            .border(2.dp, color = color, shape = CircleShape)
            .background(if (isSelected) color else Color.Transparent, shape = CircleShape)
            .padding(all = 5.dp)
        ) {
            VisibilityContainer(visible = isSelected) {
                Icon(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.drawable.ic_accept),
                    contentDescription = emptyString(),
                    tint = Color.Unspecified,
                )
            }
        }
    }
}

@Composable
fun RegularityTypeBox(
    modifier: Modifier = Modifier,
    type: RegularityType,
    weekDays: List<DayUI>,
    monthlyDays: List<DayUI>,
    color: CategoryMainColor,
    dayClickAction: (day: DayUI) -> Unit
) {
    Box {
        VisibilityContainer(visible = type is RegularityType.WeeklyType) {
            WeeklyDaysRow(daysState = weekDays, color = color) {
                dayClickAction.invoke(it)
            }
        }
        ExpandableContainer(type == RegularityType.MonthlyType) {
            MonthlyDaysRows(modifier = Modifier.fillMaxWidth(), days = monthlyDays, color = color) {
                dayClickAction.invoke(it)
            }
        }
    }
}

@Composable
fun WeeklyDaysRow(
    modifier: Modifier = Modifier,
    daysState: List<DayUI> = emptyList(),
    color: CategoryMainColor,
    onClick: (DayUI) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysState.forEach { dayState ->
            DayButton(
                dayButtonState = dayState,
                color = color,
                onClick = onClick
            )
        }
    }
}

@Composable
fun MonthlyDaysRows(
    modifier: Modifier = Modifier,
    color: CategoryMainColor,
    days: List<DayUI>,
    onClick: (DayUI) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp),
        columns = GridCells.Fixed(7),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(days, key = {day -> day.dayPosition }) {
            DayButton(
                dayButtonState = it,
                color = color,
                onClick = onClick
            )
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightMainBackground)
    ) {
        RegularityColumn(
            regularityContentUI = mutableStateListOf(),
            regularityActions = RegularityActions(),
        )
        LabelWithCheckbox(color = CategoryAccentColor.GreenAccent.pureColor, label = "I want to use time reminder") {}
        RegularityExpanderIcon(
            modifier = Modifier.fillMaxWidth(),
            isCollapsed = false,
            label = "(Month)",
            color = CategoryAccentColor.GreenAccent.pureColor
        ) {

        }
    }
}