package com.drowsynomad.mirrovision.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import com.drowsynomad.mirrovision.presentation.navigation.RootNavigation
import com.drowsynomad.mirrovision.presentation.theme.MirrovisionTheme
import com.drowsynomad.mirrovision.presentation.utils.FixedInsets
import com.drowsynomad.mirrovision.presentation.utils.LocalFixedInsets
import com.drowsynomad.mirrovision.presentation.utils.pxToDp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            MirrovisionTheme {
                val statusBarHeight = WindowInsets.statusBars.getTop(LocalDensity.current).pxToDp()
                val navigationBarHeight = WindowInsets.navigationBars.getTop(LocalDensity.current).pxToDp()

                val fixedInsets = remember {
                    FixedInsets(
                        statusBarHeight = statusBarHeight,
                        navigationBarHeight = navigationBarHeight
                    )
                }

                CompositionLocalProvider(values = arrayOf(LocalFixedInsets provides fixedInsets)) {
                    RootNavigation()
                }
            }
        }
    }
}