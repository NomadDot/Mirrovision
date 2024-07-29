package com.drowsynomad.mirrovision.presentation.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import java.util.Locale

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

object LocaleUtils {
    fun setLocale(c: Context, pref: String) = updateResources(c, pref)

    private fun updateResources(context: Context, language: String) {
        Log.i("Language", language)
        context.resources.apply {
            val locale = Locale(language)
            val config = Configuration(configuration)

            context.createConfigurationContext(configuration)
            Locale.setDefault(locale)
            config.setLocale(locale)

            context.resources.updateConfiguration(config, displayMetrics)
        }
    }
}