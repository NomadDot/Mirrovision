package com.drowsynomad.mirrovision.presentation.screens.chooseIcon

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.core.base.StateContent
import com.drowsynomad.mirrovision.presentation.core.components.CancelableAndSaveableButton
import com.drowsynomad.mirrovision.presentation.core.components.CustomizedToolbar
import com.drowsynomad.mirrovision.presentation.core.components.DefaultProgress
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.model.ChooseIconEvent
import com.drowsynomad.mirrovision.presentation.screens.chooseIcon.model.ChooseIconState
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.bounceClick
import com.drowsynomad.mirrovision.presentation.utils.roundBox
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 22.07.2024)
 */

@Composable
fun ChooseIconScreen(
    viewModel: ChooseIconVM,
    categoryMainColor: CategoryMainColor,
    onBackNavigation: Navigation,
    onIconSelected: (Int) -> Unit
) {
    StateContent(
        viewModel = viewModel,
        launchedEffect = { viewModel.handleUiEvent(ChooseIconEvent.LoadIcons) },
        contentBackground = categoryMainColor.pureColor
    ) {
        if(it.isLoading)
            DefaultProgress()
        else
            ChooseIconContent(
                state = it,
                color = categoryMainColor,
                onIconSelected = onIconSelected,
                onIconClick = { viewModel.handleUiEvent(ChooseIconEvent.SelectIcon(it))} ,
                onBackNavigation = onBackNavigation)
    }
}

@Composable
private fun ChooseIconContent(
    state: ChooseIconState,
    color: CategoryMainColor,
    onIconSelected: (Int) -> Unit,
    onIconClick: (HabitIcon) -> Unit,
    onBackNavigation: Navigation
) {
    Box(modifier = Modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            CustomizedToolbar(
                accentColor = color.accent.pureColor,
                backgroundColor = color.pureColor,
                title = stringResource(R.string.label_icons)
            ) { onBackNavigation.invoke() }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                contentPadding = PaddingValues(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(state.icons) {
                    IconCategory(
                        title = it.title,
                        color = color,
                        icons = it.icons,
                        onIconClick = onIconClick
                    )
                }
            }
        }
        CancelableAndSaveableButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            containerColor = color.accent.pureColor,
            cancelButtonLabel = R.string.button_cancel,
            primaryButtonLabel = R.string.button_save,
            isPrimaryButtonEnabled = state.isSavingEnabled,
            onCancelButtonClick = onBackNavigation,
            onPrimaryButtonClick =  {
                state.selectedIcon?.let {
                    onIconSelected.invoke(it)
                }
            }
        )
    }
}

data class IconsInCategory(
    val title: String,
    val icons: SnapshotStateList<HabitIcon> = mutableStateListOf(),
    val id: Long = Random.nextLong(),
)

data class HabitIcon(
    val id: Int = Random.nextInt(),
    val category: String = emptyString(),
    @DrawableRes val icon: Int,
    val initialSelection: Boolean = false
) {
    val isSelected = mutableStateOf(initialSelection)
}

@Composable
private fun IconCategory(
    modifier: Modifier = Modifier,
    title: String,
    icons: SnapshotStateList<HabitIcon>,
    color: CategoryMainColor,
    onIconClick: (HabitIcon) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = color.accent.pureColor,
                modifier = Modifier.padding(start = 24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .padding(horizontal = 24.dp)
                    .height(200.dp)
                    .roundBox(Color.White)
                    .align(Alignment.CenterHorizontally)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .align(Alignment.Center),
                    columns = GridCells.Fixed(6),
                    userScrollEnabled = false,
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    items(icons, key = { icon -> icon.id }) {
                        CategoryIcon(habitIcon = it, color = color, onIconClick = onIconClick)
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryIcon(
    modifier: Modifier = Modifier,
    habitIcon: HabitIcon,
    color: CategoryMainColor,
    onIconClick: (HabitIcon) -> Unit
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .bounceClick { onIconClick.invoke(habitIcon) }
    ) {
        AnimatedVisibility(
            visible = habitIcon.isSelected.value,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    color = color.pureColor,
                    shape = CircleShape
                )
            )
        }
        Icon(
            painter = painterResource(id = habitIcon.icon),
            contentDescription = habitIcon.id.toString(),
            tint = color.accent.pureColor,
            modifier = Modifier
                .size(45.dp)
                .align(Alignment.Center)
        )
    }
}