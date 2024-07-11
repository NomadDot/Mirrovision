package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI

/**
 * @author Roman Voloshyn (Created on 09.07.2024)
 */

@Composable
fun HabitItem(
    habit: HabitUI,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        HabitIcon(
            modifier = Modifier.width(50.dp).height(50.dp),
            iconSpec = if(habit.isDefaultIcon) 16.dp else 30.dp,
            accentColor = habit.backgroundColor.pureColor,
            iconTint = habit.accentColor.pureColor,
            icon = R.drawable.ic_add,
            outerRadius = 5.dp
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        HabitItem(HabitUI())
    }
}