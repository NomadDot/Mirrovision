package com.drowsynomad.mirrovision.presentation.utils

import android.content.Context
import androidx.annotation.StringRes

/**
 * @author Roman Voloshyn (Created on 10.07.2024)
 */

interface IStringConverterManager {
    fun getString(@StringRes stringId: Int): String
}

class StringConverterManager(private val applicationContext: Context): IStringConverterManager {
    override fun getString(@StringRes stringId: Int): String = applicationContext.getString(stringId)
}