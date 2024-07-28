package com.drowsynomad.mirrovision.presentation.utils

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import java.util.Locale

/**
 * @author Roman Voloshyn (Created on 27.07.2024)
 */

object LocaleUtils {
    fun updateLanguage(languageId: String) =
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(
                languageId.dropLast(1).lowercase(Locale.getDefault())
            )
        )

    fun setLocale(c: Context, pref: String) = updateResources(c, pref) //use locale codes

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