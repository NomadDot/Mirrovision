package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.StrokeWidth
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.CategoryAssets
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

@Composable
fun HabitCategory(
    modifier: Modifier = Modifier,
    category: CategoryUI = CategoryUI(),
    onLongHabitClick:((HabitUI) -> Unit)? = null,
    onHabitClick: ((HabitUI) -> Unit)? = null
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 23.dp, top = 10.dp)
            .roundBox(category.backgroundColor.pureColor), // TODO: Change logic of color
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            BigTitle(text = category.name, color = category.backgroundColor.accent.pureColor)
            Box(modifier = Modifier.fillMaxWidth()) {
                HabitRow(
                    category.habits,
                    categoryAssets = CategoryAssets(category.id, category.backgroundColor),
                    Modifier,
                    onHabitClick = onHabitClick,
                    onLongHabitClick = onLongHabitClick
                )
                if(category.isFirstHabitPreset)
                    AdviceText(text = stringResource(R.string.label_create_your_first_habit),
                        modifier = Modifier.align(Alignment.BottomEnd),
                        color = category.backgroundColor.accent.pureColor
                    )
            }
        }
        CategoryIcon(
            color = category.backgroundColor,
            icon = category.iconRes,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 14.dp)
                .width(50.dp)
                .height(50.dp)
        )
    }
}

@Composable
private fun HabitRow(
    habits: SnapshotStateList<HabitUI>,
    categoryAssets: CategoryAssets,
    modifier: Modifier = Modifier,
    onHabitClick: ((HabitUI) -> Unit)? = null,
    onLongHabitClick: ((HabitUI) -> Unit)? = null
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(habits, key = { item -> item.id }) {
            AmountHabit(
                iconSize = 40.dp,
                strokeSize = 80.dp,
                strokeWidth = StrokeWidth.Custom(16f),
                habitUI = it,
                onLongHabitClick = { onLongHabitClick?.invoke(it) }
            ) {
                onHabitClick?.invoke(it)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HabitCategory(
            category = CategoryUI(backgroundColor = CategoryMainColor.Blue)
        ) {}
    }
}