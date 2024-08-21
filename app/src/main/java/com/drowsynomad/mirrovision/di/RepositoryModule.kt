package com.drowsynomad.mirrovision.di

import com.drowsynomad.mirrovision.data.assets.IAssetStore
import com.drowsynomad.mirrovision.data.dataStore.DataStorePreferences
import com.drowsynomad.mirrovision.data.database.MirrovisionDatabase
import com.drowsynomad.mirrovision.domain.categories.CategoryRepository
import com.drowsynomad.mirrovision.domain.categories.ICategoryRepository
import com.drowsynomad.mirrovision.domain.habit.HabitRecordingRepository
import com.drowsynomad.mirrovision.domain.habit.HabitRegularityRepository
import com.drowsynomad.mirrovision.domain.habit.HabitRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRecordingRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRegularityRepository
import com.drowsynomad.mirrovision.domain.habit.IHabitRepository
import com.drowsynomad.mirrovision.domain.language.ILanguageRepository
import com.drowsynomad.mirrovision.domain.language.LanguageRepository
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
    fun provideHabitRecordingRepository(database: MirrovisionDatabase): IHabitRecordingRepository =
        HabitRecordingRepository(database)

    @Provides
    @ViewModelScoped
    fun provideHabitRegularityRepository(database: MirrovisionDatabase): IHabitRegularityRepository =
        HabitRegularityRepository(database)

    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        dataStorePreferences: DataStorePreferences,
        assetStore: IAssetStore
    ): IUserRepository =
        UserRepository(dataStorePreferences, assetStore)

    @Provides
    @ViewModelScoped
    fun provideLocalizationRepository(
        dataStorePreferences: DataStorePreferences,
        assetStore: IAssetStore
    ): ILanguageRepository =
        LanguageRepository(dataStorePreferences, assetStore)
}