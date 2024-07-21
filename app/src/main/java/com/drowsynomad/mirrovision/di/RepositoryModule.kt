package com.drowsynomad.mirrovision.di

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.domain.categories.CategoryRepository
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.habit.HabitRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.domain.user.IUserRepository
import com.drowsynomad.mirrovision.domain.user.UserRepository
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
    fun provideCategoryRepository(assetStore: IAssetStore, database: MirrovisionDatabase): ICategoryRepository =
        CategoryRepository(assetStore, database)

    @Provides
    @ViewModelScoped
    fun provideHabitRepository(database: MirrovisionDatabase): IHabitRepository =
        HabitRepository(database)

    @Provides
    @ViewModelScoped
    fun provideUserRepository(dataStorePreferences: DataStorePreferences): IUserRepository =
        UserRepository(dataStorePreferences)
}