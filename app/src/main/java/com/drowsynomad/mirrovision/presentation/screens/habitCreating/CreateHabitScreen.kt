package com.drowsynomad.mirrovision.presentation.screens.habitCreating

import android.os.Parcelable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.AddingButton
import com.drowsynomad.mirrovision.presentation.core.components.BackButton
import com.drowsynomad.mirrovision.presentation.core.components.BigTitle
import com.drowsynomad.mirrovision.presentation.core.components.CancelableAndSaveableButton
import com.drowsynomad.mirrovision.presentation.core.components.ExplainTitle
import com.drowsynomad.mirrovision.presentation.core.components.HabitCounter
import com.drowsynomad.mirrovision.presentation.core.components.HabitIcon
import com.drowsynomad.mirrovision.presentation.core.components.InputField
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.habitCreating.model.CreateHabitState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.LocalFixedInsets
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * @author Roman Voloshyn (Created on 11.07.2024)
 */

@Serializable
@Parcelize
data class CategoryAssets(
    val categoryId: Int,
    val color: CategoryMainColor
): Parcelable

@Composable
fun CreateHabitScreen(
    viewModel: CreateHabitVM,
    categoryAsset: CategoryAssets,
    onBackNavigation: Navigation,
    onSaveHabit: () -> Unit
) {
    StateContent(
        isStatusBarPadding = false,
        viewModel = viewModel,
        launchedEffect = {
        }
    ) {
        CreateHabitContent(it, categoryAsset, onBackNavigation, onSaveHabit)
    }
}

@Composable
fun IconItem(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = Modifier) {
        Canvas(modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
            .clipToBounds()
        ) {
            drawCircle(
                color = color,
                radius = size.width/1.8f,
                center = Offset(this.center.x, y = 0f)
            )
        }
        HabitIcon(
            modifier = Modifier
                .padding(top = 60.dp + LocalFixedInsets.current.statusBarHeight)
                .size(115.dp)
                .align(Alignment.TopCenter),
            outerRadius = 12.dp,
            iconSpec = 60.dp,
            accentColor = color
        )
    }
}

@Composable
fun CreateHabitContent(
    state: CreateHabitState,
    categoryAsset: CategoryAssets,
    onBackNavigation: Navigation,
    onSaveHabit: () -> Unit
) {
    val accentColor = categoryAsset.color.accent.pureColor
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            IconItem(categoryAsset.color.pureColor)
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
                    hint = stringResource(R.string.label_habit_enter_the_name)
                )
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
                    hint = stringResource(R.string.label_add_habit_description)
                )
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
                    styleColor = categoryAsset.color.pureColor,
                    accentColor = accentColor
                )
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
            onPrimaryButtonClick = {
                onSaveHabit.invoke()
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

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        CreateHabitContent(state = CreateHabitState(), CategoryAssets(0, CategoryMainColor.Red), {}, {})
    }
}