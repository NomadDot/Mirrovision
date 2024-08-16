package com.drowsynomad.mirrovision.di

import android.content.Context
import com.drowsynomad.mirrovision.presentation.utils.IStringConverterManager
import com.drowsynomad.mirrovision.presentation.utils.StringConverterManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @author Roman Voloshyn (Created on 10.07.2024)
 */

@Module
@InstallIn(ViewModelComponent::class)
object UtilModule {
    @Provides
    @ViewModelScoped
    fun provideStringManager(@ApplicationContext applicationContext: Context): IStringConverterManager =
        StringConverterManager(applicationContext)
}