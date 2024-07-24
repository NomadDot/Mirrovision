package com.drowsynomad.mirrovision.presentation.utils

import android.content.Context
import androidx.annotation.ArrayRes
import androidx.annotation.StringRes

/**
 * @author Roman Voloshyn (Created on 10.07.2024)
 */

interface IStringConverterManager {
    fun getString(@StringRes stringId: Int): String
    fun getStringArray(@ArrayRes stringId: Int): Array<String>
}

class StringConverterManager(private val applicationContext: Context): IStringConverterManager {
    override fun getString(@StringRes stringId: Int): String = applicationContext.getString(stringId)
    override fun getStringArray(@ArrayRes stringId: Int): Array<String> =
        applicationContext.resources.getStringArray(stringId)
}