package ua.wied.data.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.wied.data.UserPreferencesConstants.SETTINGS_PREFERENCES
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.SettingsPreference
import ua.wied.data.di.StorageModule
import ua.wied.data.repository.SettingsRepositoryImpl
import ua.wied.domain.repository.SettingsRepository
import ua.wied.domain.usecases.GetLanguageUseCase
import ua.wied.domain.usecases.GetSettingsUseCase
import ua.wied.domain.usecases.IsDarkThemeEnabledUseCase
import ua.wied.domain.usecases.ObserveSettingsUseCase
import ua.wied.domain.usecases.SetDarkThemeEnabledUseCase
import ua.wied.domain.usecases.SetLanguageUseCase
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class SettingsModule {

    @Provides
    @Singleton
    fun provideGetLanguageUseCase(
        repo: SettingsRepository
    ): GetLanguageUseCase = GetLanguageUseCase(repo)

    @Provides
    @Singleton
    fun provideSetLanguageUseCase(
        repo: SettingsRepository
    ): SetLanguageUseCase = SetLanguageUseCase(repo)

    @Provides
    @Singleton
    fun provideIsDarkThemeEnabledUseCase(
        repo: SettingsRepository
    ): IsDarkThemeEnabledUseCase = IsDarkThemeEnabledUseCase(repo)

    @Provides
    @Singleton
    fun provideSetDarkThemeEnabledUseCase(
        repo: SettingsRepository
    ): SetDarkThemeEnabledUseCase = SetDarkThemeEnabledUseCase(repo)

    @Provides
    @Singleton
    fun provideGetSettingsUseCase(
        repo: SettingsRepository
    ): GetSettingsUseCase = GetSettingsUseCase(repo)

    @Provides
    @Singleton
    fun provideObserveSettingsUseCase(
        repo: SettingsRepository
    ): ObserveSettingsUseCase = ObserveSettingsUseCase(repo)

    @Provides
    @Singleton
    fun provideSettingsRepository(@SettingsPreference dataStore: DataStore<Preferences>, @ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(dataStore = dataStore, context = context)
    }

    @Provides
    @Singleton
    @SettingsPreference
    fun provideSettingsStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return StorageModule.createPreferenceDataStore(context, SETTINGS_PREFERENCES)
    }
}