package com.drowsynomad.mirrovision.di

import android.content.Context
import com.drowsynomad.mirrovision.data.assets.AssetsStore
import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MirrovisionDatabase =
        MirrovisionDatabase.getInstance(context)

    @Provides
    @Singleton
    fun providesAssetsStore(@ApplicationContext context: Context): IAssetStore =
        AssetsStore.getInstance(context)
}