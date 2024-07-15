package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.theme.LightPrimary

/**
 * @author Roman Voloshyn (Created on 09.07.2024)
 */

@Composable
fun HabitItem(
    habit: HabitUI,
    categoryAssets: CategoryAssets,
    modifier: Modifier = Modifier,
    onCreateHabit: (CategoryAssets) -> Unit,
) {
    Box(modifier = modifier) {
        HabitIcon(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp),
            iconSpec = if(habit.isDefaultIcon) 16.dp else 30.dp,
            accentColor = habit.backgroundColor.pureColor,
            iconTint = habit.accentColor.pureColor,
            icon = R.drawable.ic_add,
            outerRadius = 5.dp
        ) {
            onCreateHabit.invoke(categoryAssets)
        }
    }
}

@Composable
fun HabitCounter(
    modifier: Modifier = Modifier,
    defaultValue: Int = 1,
    styleColor: Color = LightPrimary,
    accentColor: Color
) {
    val countValue = remember {
        mutableIntStateOf(defaultValue)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        PrimaryButton(
            icon = R.drawable.ic_minus,
            isEnabled = countValue.intValue > 1,
            containerColor = styleColor,
            modifier = Modifier.width(width = 100.dp),
        ) {
            countValue.intValue -= 1
        }
        CounterText(
            countValue.intValue.toString(),
            modifier = Modifier.weight(2f),
            color = accentColor
        )
        PrimaryButton(
            modifier = Modifier.width(width = 100.dp),
            icon = R.drawable.ic_add,
            containerColor = styleColor,
        ) {
            countValue.intValue += 1
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column {
//        HabitItem(HabitUI(attachedCategory = CategoryUI()))
        HabitCounter(defaultValue = 30, accentColor = LightPrimary)
    }
}