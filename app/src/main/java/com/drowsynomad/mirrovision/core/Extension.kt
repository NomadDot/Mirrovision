package com.drowsynomad.mirrovision.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * @author Roman Voloshyn (Created on 23.06.2024)
 */

fun emptyString(): String = ""

fun Int.isNegative(): Boolean = this < 0.0f
fun Int.isZero(): Boolean = this == 0

@Composable
@ReadOnlyComposable
fun getLocale(): Locale {
    val configuration = LocalConfiguration.current
    return ConfigurationCompat.getLocales(configuration).get(0) ?: LocaleListCompat.getDefault()[0]!!
}