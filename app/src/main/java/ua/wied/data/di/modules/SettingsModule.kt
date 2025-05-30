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
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class SettingsModule {

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