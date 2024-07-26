package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.common.models.CategoryUI
import com.drowsynomad.mirrovision.presentation.core.common.models.HabitUI
import com.drowsynomad.mirrovision.presentation.core.common.models.StrokeWidth
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.ExpandableContainer
import com.drowsynomad.mirrovision.presentation.utils.VisibilityContainer
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 01.07.2024)
 */

@Composable
fun HabitCategory(
    modifier: Modifier = Modifier,
    category: CategoryUI = CategoryUI(),
    onEditHabit: ((HabitUI) -> Unit)? = null,
    onLongHabitClick:((HabitUI) -> Unit)? = null,
    onHabitClick: ((HabitUI) -> Unit)? = null
) {
    TransitionHabitCategory(modifier, category, onEditHabit, onLongHabitClick, onHabitClick)
}

@Composable
private fun TransitionHabitCategory(
    modifier: Modifier = Modifier,
    category: CategoryUI = CategoryUI(),
    onEditHabit: ((HabitUI) -> Unit)? = null,
    onLongHabitClick:((HabitUI) -> Unit)? = null,
    onHabitClick: ((HabitUI) -> Unit)? = null
) {
    val isCategoryExpanded = rememberSaveable { mutableStateOf(false) }

    val accent = category.backgroundColor.accent.pureColor

    Box(modifier = modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .animateContentSize(
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        ),
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 23.dp, top = 10.dp)
            .roundBox(category.backgroundColor.pureColor),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AnimatedVisibility(visible = !isCategoryExpanded.value) {
                    Icon(
                        modifier = Modifier
                            .clickable { isCategoryExpanded.value = true }
                            .padding(end = 15.dp),
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = emptyString(),
                        tint = category.backgroundColor.accent.pureColor)
                }
                BigTitle(text = category.name, color = category.backgroundColor.accent.pureColor)
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                VisibilityContainer(visible = !isCategoryExpanded.value) {
                    HabitRow(category.habits, Modifier, onHabitClick = onHabitClick, onLongHabitClick = onLongHabitClick)
                }
                 ExpandableContainer(isExpanded = isCategoryExpanded.value) {
                     Column {
                         ConfigurationButton(
                             modifier = Modifier.fillMaxWidth(),
                             icon = R.drawable.ic_settings, containerColor = accent,
                             text = stringResource(R.string.label_edit_category)
                         )
                         ConfigurationButton(
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .padding(top = 15.dp),
                             icon = R.drawable.ic_add,
                             containerColor = accent,
                             text = stringResource(R.string.label_add_habit)
                         ) {
                             onEditHabit?.invoke(
                                 HabitUI(
                                     backgroundColor = category.backgroundColor,
                                     attachedCategoryId = category.id
                                 )
                             )
                         }
                         EditHabitColumn(
                             category.habits,
                             Modifier.padding(top = 10.dp),
                             onEditHabit = onEditHabit,
                             onHabitClick = onHabitClick,
                             onLongHabitClick = onLongHabitClick
                         )
                     }
                 }
                if(category.isFirstHabitPreset)
                    AdviceText(text = stringResource(R.string.label_create_your_first_habit),
                        modifier = Modifier.align(Alignment.BottomEnd),
                        color = category.backgroundColor.accent.pureColor)
            }
        }
        if(isCategoryExpanded.value)
            Icon(
                modifier = Modifier
                    .clickable { isCategoryExpanded.value = false }
                    .align(TopEnd)
                    .padding(end = 24.dp + 16.dp, top = 35.dp),
                painter = painterResource(id = R.drawable.ic_cancel),
                contentDescription = emptyString(),
                tint = category.backgroundColor.accent.pureColor)
        AnimatedVisibility(
            modifier = Modifier
                .align(TopEnd)
                .wrapContentSize(),
            visible = !isCategoryExpanded.value,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            CategoryIcon(
                color = category.backgroundColor,
                icon = category.iconRes,
                modifier = Modifier
                    .align(TopEnd)
                    .padding(end = 14.dp)
                    .width(50.dp)
                    .height(50.dp)
            )
        }
    }
}

@Composable
private fun HabitRow(
    habits: SnapshotStateList<HabitUI>,
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
                iconSize = 60.dp,
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

@Composable
private fun EditHabitColumn(
    habits: SnapshotStateList<HabitUI>,
    modifier: Modifier = Modifier,
    onEditHabit: ((HabitUI) -> Unit)? = null,
    onHabitClick: ((HabitUI) -> Unit)? = null,
    onLongHabitClick: ((HabitUI) -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        habits.forEach {
            EditAmountHabit(habitUI = it, Modifier.fillMaxWidth(), onEditHabit, onHabitClick, onLongHabitClick)
        }
    }
}

@Composable
fun EditAmountHabit(
    habitUI: HabitUI,
    modifier: Modifier = Modifier,
    onEditHabit: ((HabitUI) -> Unit)? = null,
    onHabitClick: ((HabitUI) -> Unit)? = null,
    onLongHabitClick: ((HabitUI) -> Unit)? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        AmountHabit(
            iconSize = 45.dp,
            strokeSize = 60.dp,
            strokeWidth = StrokeWidth.Custom(11f),
            habitUI = habitUI,
            onLongHabitClick = { onLongHabitClick?.invoke(habitUI) }
        ) {
            onHabitClick?.invoke(habitUI)
        }
        ConfigurationButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.label_edit_habit),
            icon = R.drawable.ic_settings,
            containerColor = habitUI.backgroundColor.accent.pureColor
        ) {
            onEditHabit?.invoke(habitUI)
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