package com.drowsynomad.mirrovision.presentation.core.components

import android.annotation.SuppressLint
import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.drowsynomad.mirrovision.R
import com.drowsynomad.mirrovision.core.emptyString
import com.drowsynomad.mirrovision.presentation.navigation.MainBottomNavItem
import com.drowsynomad.mirrovision.presentation.navigation.Navigation
import com.drowsynomad.mirrovision.presentation.navigation.Routes
import com.drowsynomad.mirrovision.presentation.theme.GradientMain
import com.drowsynomad.mirrovision.presentation.utils.EmptyNavigationBarItem
import com.drowsynomad.mirrovision.presentation.utils.clearRoute
import com.drowsynomad.mirrovision.presentation.utils.gradient

/**
 * @author Roman Voloshyn (Created on 25.06.2024)
 */

data class BottomNavigationActions(
    val onHomeNavigation: Navigation? = null,
    val onTimerNavigation: Navigation? = null,
    val onStatisticNavigation: Navigation? = null,
    val onNotesNavigation: Navigation? = null,
    val onTutorialNavigation: Navigation? = null
) {
    fun getNavigationByRoute(route: Routes): Navigation? {
        return when(route) {
            Routes.GuideScreen -> onTutorialNavigation
            Routes.HomeScreen -> onHomeNavigation
            Routes.NotesScreen -> onNotesNavigation
            Routes.StatisticScreen -> onStatisticNavigation
            Routes.TimerScreen -> onTimerNavigation
            else -> onHomeNavigation
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    bottomNavigationActions: BottomNavigationActions? = null
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .padding(horizontal = 10.dp)
            .background(color = Color.Transparent),
        containerColor = Color.Transparent
    ) {
        Box(modifier = Modifier
            .wrapContentWidth()
            .height(70.dp)
            .background(color = Color.Transparent)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .gradient(GradientMain)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                ) {
                    MainBottomNavItem.values.forEachIndexed { _, item ->
                        if(item is MainBottomNavItem.Empty) 
                            EmptyNavigationBarItem()
                        else
                            this@NavigationBar.NavigationBarItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = item.icon),
                                        contentDescription = emptyString(),
                                        tint = Color.White)
                                },
                                selected = currentDestination?.hierarchy
                                    ?.any { it.route.clearRoute() == item.route.toString() } == true,
                                onClick = {
                                    bottomNavigationActions
                                        ?.getNavigationByRoute(item.route)?.invoke()
                                }
                            )
                    }
                }
            }
            Semicircle(Modifier.align(Alignment.Center))
            HomeButton(modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeButton(modifier: Modifier = Modifier) {
    val selected = remember { mutableStateOf(false) }
    val scale = animateFloatAsState(if (selected.value) 0.7f else 1f, label = emptyString())

    Icon(
        painter = painterResource(id = R.drawable.ic_home),
        contentDescription = emptyString(),
        modifier = modifier
            .scale(scale.value)
            .offset(y = (-15).dp)
            .size(50.dp)
            .gradient(GradientMain)
            .padding(12.dp)
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> selected.value = true
                    MotionEvent.ACTION_UP -> selected.value = false
                }
                true
            },
        tint = Color.White
    )
}

@Composable
private fun Semicircle(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier
        .size(60.dp)
        .clipToBounds()
        .offset(y = (-15.1).dp)
    ) {
        drawArc(
            color = Color.White,
            0f,
            180f,
            useCenter = true,
            size = Size(size.width, 60.dp.toPx())
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NavigationBarPreview() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White), contentAlignment = Alignment.BottomCenter
    ) {
        // NavigationBar(modifier = Modifier.align(Alignment.BottomCenter))
        BottomNavigationBar()
    }
}