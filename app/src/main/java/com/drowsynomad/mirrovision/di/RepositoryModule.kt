package com.drowsynomad.mirrovision.di

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.domain.categories.CategoryRepository
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @author Roman Voloshyn (Created on 27.06.2024)
 */

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideMoviesRepository(assetStore: IAssetStore): ICategoryRepository =
        CategoryRepository(assetStore)
}