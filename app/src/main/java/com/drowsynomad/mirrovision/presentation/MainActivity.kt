package com.drowsynomad.mirrovision.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.drowsynomad.mirrovision.presentation.navigation.RootNavigation
import com.drowsynomad.mirrovision.presentation.theme.MirrovisionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MirrovisionTheme {
                RootNavigation()
            }
        }
    }
}