package com.drowsynomad.mirrovision.presentation.utils

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import com.drowsynomad.mirrovision.R

/**
 * @author Roman Voloshyn (Created on 10.07.2024)
 */

interface IStringConverterManager {
    fun getString(@StringRes stringId: Int): String
    fun getStringArray(@ArrayRes stringId: Int): Array<String>

    fun getLocalizationByKey(key: String): Int
}

class StringConverterManager(private val applicationContext: Context): IStringConverterManager {
    override fun getString(@StringRes stringId: Int): String = applicationContext.getString(stringId)

    override fun getStringArray(@ArrayRes stringId: Int): Array<String> =
        applicationContext.resources.getStringArray(stringId)

    override fun getLocalizationByKey(key: String): Int {
        return when(key) {
            "uk" -> R.string.localization_ua
            "en" -> R.string.localization_eng
            else -> R.string.localization_eng
        }
    }
}