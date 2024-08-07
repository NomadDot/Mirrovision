package com.drowsynomad.mirrovision.presentation.core.components

import android.os.Parcelable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
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
import com.drowsynomad.mirrovision.presentation.core.components.RegularityContentUI.Companion.getDefaultRegularity
import com.drowsynomad.mirrovision.presentation.theme.CategoryAccentColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.theme.LightMainBackground
import com.drowsynomad.mirrovision.presentation.theme.ShadowColor
import com.drowsynomad.mirrovision.presentation.utils.ExpandableContainer
import com.drowsynomad.mirrovision.presentation.utils.StringConverterManager
import com.drowsynomad.mirrovision.presentation.utils.VisibilityContainer
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.fillMonthlyDays
import com.drowsynomad.mirrovision.presentation.utils.fillWeeklyDays
import com.drowsynomad.mirrovision.presentation.utils.formatTime
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import com.drowsynomad.mirrovision.presentation.utils.toTime
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 01.08.2024)
 */

@Composable
fun RegularityColumn(
    modifier: Modifier = Modifier,
    color: CategoryMainColor = CategoryMainColor.Green,
    regularityContentUI: List<RegularityContentUI>,
    onUpdated: (List<RegularityContentUI>) -> Unit
) {
    val context = LocalContext.current
    val weeklyDayLabels = remember {
        StringConverterManager(context)
            .getStringArray(R.array.weekly_days).toList()
    }
    val regularity = remember {
        if(regularityContentUI.isEmpty())
            listOf(
                getDefaultRegularity(RegularityType.WeeklyType(weeklyDayLabels), false)
            ).toMutableStateList()
        else
            regularityContentUI.toMutableStateList()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        regularity.forEach {
            RegularityPickerItem(
                state = it,
                color = color,
                cancellable = it.cancellable,
                onRemoveRegularity = { out ->
                    regularity.remove(out)
                }
            )
        }
        AddingButton(
            modifier = Modifier
                .fillMaxWidth(),
            color = color.accent.pureColor,
        ) {
            regularity.add(
                getDefaultRegularity(RegularityType.WeeklyType(weeklyDayLabels), true)
            )
        }
    }
}

@Serializable
@Parcelize
data class RegularityContentUI(
    val id: Int = Random.nextInt(),
    val presetTime: String = emptyString(),
    val useTime: Boolean = false,
    val cancellable: Boolean = false,
    val type: RegularityType = RegularityType.WeeklyType(),
    val days: List<DayState> = emptyList()
): Parcelable {
    companion object {
        fun getDefaultRegularity(type: RegularityType, cancellable: Boolean = false): RegularityContentUI {
            val currentLocalTime = DateTime.now().toLocalTime()
            val currentTime = "${currentLocalTime.hourOfDay}:${currentLocalTime.minuteOfHour}"
                .formatTime()

            val days =
                if(type is RegularityType.WeeklyType) fillWeeklyDays(type.localizedDayNames)
                else fillMonthlyDays()

            return RegularityContentUI(presetTime = currentTime, days = days, cancellable = cancellable)
        }
    }
}

@Composable
private fun RegularityPickerItem(
    modifier: Modifier = Modifier,
    state: RegularityContentUI,
    color: CategoryMainColor = CategoryMainColor.Green,
    cancellable: Boolean = false,
    onRemoveRegularity: (RegularityContentUI) -> Unit,
    onChangeTime: ((String) -> Unit)? = null,
    onDaysChanged: ((List<Int>) -> Unit)? = null,
    onTypeChanged: ((RegularityType) -> Unit)? = null
) {
    val type = remember {
        mutableStateOf(state.type)
    }
    val time = remember {
        mutableStateOf(state.presetTime)
    }
    val useTime = remember {
        mutableStateOf(state.useTime)
    }

    val showTimeSelector = remember { mutableStateOf(false) }

    if(showTimeSelector.value)
        TimePickerDialog(
            presetTime = time.value.toTime(),
            color = color,
            onConfirm = {
                time.value = it.formattedTime
                showTimeSelector.value = false
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
                    VisibilityContainer(visible = useTime.value) {
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
                                text = time.value,
                                style = MaterialTheme.typography.bodyMedium,
                                color = color.accent.pureColor,
                                fontSize = 32.sp
                            )
                        }
                    }
                    VisibilityContainer(visible = !useTime.value) {
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
                                 if(type.value is RegularityType.WeeklyType) R.string.label_weekly_brackets
                                 else  R.string.label_monthly_brackets
                            ),
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
                                    onRemoveRegularity.invoke(state)
                                }
                        )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LabelWithCheckbox(
                modifier = Modifier.fillMaxWidth(),
                color = color.accent.pureColor,
                isSelected = useTime.value,
                label = stringResource(R.string.i_want_to_use_time_reminder)
            ) {
                useTime.value = !useTime.value
            }
            Spacer(modifier = Modifier.height(16.dp))
            RegularityTypeBox(
                type = type.value,
                days = state.days,
                color = color
            )
            RegularityExpanderIcon(
                modifier = Modifier.padding(top = 16.dp),
                isCollapsed = type.value is RegularityType.WeeklyType,
                color = color.accent.pureColor,
                label = stringResource(
                    if(type.value is RegularityType.WeeklyType) R.string.label_monthly_brackets
                    else  R.string.label_weekly_brackets
                ),
            ) {
                if (type.value is RegularityType.WeeklyType)
                    type.value = RegularityType.MonthlyType
                else
                    type.value = RegularityType.WeeklyType()
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
    days: List<DayState>,
    color: CategoryMainColor
) {
    Box {
        VisibilityContainer(visible = type is RegularityType.WeeklyType) {
            WeeklyDaysRow(daysState = days, color = color)
        }
        ExpandableContainer(type == RegularityType.MonthlyType) {
            MonthlyDaysRows(modifier = Modifier.fillMaxWidth(), color = color)
        }
    }
}

@Serializable
@Parcelize
data class DayState(
    val dayPosition: Int,
    val dayName: String,
    val initialSelection: Boolean = false
): Parcelable {
    @IgnoredOnParcel
    val isSelected = mutableStateOf(initialSelection)
}

@Composable
fun WeeklyDaysRow(
    modifier: Modifier = Modifier,
    daysState: List<DayState> = emptyList(),
    color: CategoryMainColor
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        daysState.forEach { dayState ->
            DayButton(
                dayButtonState = dayState,
                color = color
            ) {
                it.isSelected   .value = it.isSelected.value.not()
            }
        }
    }
}

@Composable
fun MonthlyDaysRows(
    modifier: Modifier = Modifier,
    color: CategoryMainColor
) {
    val days = remember {
        List(31) {
            val day = it + 1
            DayState(dayPosition = day, dayName = day.toString())
        }
    }

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
                color = color
            ) {
                it.isSelected.value = it.isSelected.value.not()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightMainBackground)
    ) {
        RegularityColumn(regularityContentUI = emptyList()) {}
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