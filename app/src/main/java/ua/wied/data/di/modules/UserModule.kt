package ua.wied.data.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ua.wied.data.UserPreferencesConstants.USER_STORAGE_PREFERENCES_NAME
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.di.UserStoragePreference
import ua.wied.data.repository.UserStoreManagerImpl
import ua.wied.domain.repository.UserStoreManager
import ua.wied.domain.usecases.ClearUserDataUseCase
import ua.wied.domain.usecases.GetUserUseCase
import ua.wied.domain.usecases.SaveUserUseCase
import ua.wied.domain.usecases.UpdateUserDataUseCase
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideUserStoreManager(@UserStoragePreference dataStore: DataStore<Preferences>): UserStoreManager {
        return UserStoreManagerImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    @UserStoragePreference
    fun provideUserStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return StorageModule.createPreferenceDataStore(context, USER_STORAGE_PREFERENCES_NAME)
    }

    @Provides
    fun provideSaveUserUseCase(userStoreManager: UserStoreManager): SaveUserUseCase {
        return SaveUserUseCase(userStoreManager)
    }

    @Provides
    fun provideGetUserUseCase(userStoreManager: UserStoreManager): GetUserUseCase {
        return GetUserUseCase(userStoreManager)
    }

    @Provides
    fun provideUpdateUserDataUseCase(userStoreManager: UserStoreManager): UpdateUserDataUseCase {
        return UpdateUserDataUseCase(userStoreManager)
    }

    @Provides
    fun provideClearUserDataUseCase(userStoreManager: UserStoreManager): ClearUserDataUseCase {
        return ClearUserDataUseCase(userStoreManager)
    }

}