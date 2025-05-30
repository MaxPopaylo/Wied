package ua.wied.data.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.wied.data.NetworkKeys.BASE_URL
import ua.wied.data.UserPreferencesConstants.USER_STORAGE_PREFERENCES_NAME
import ua.wied.data.datasource.network.api.UserApi
import ua.wied.data.di.AuthenticatedClient
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.di.UserStoragePreference
import ua.wied.data.repository.UserRepositoryImpl
import ua.wied.domain.repository.UserRepository
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
    @UserStoragePreference
    fun provideUserStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return StorageModule.createPreferenceDataStore(context, USER_STORAGE_PREFERENCES_NAME)
    }

    @Provides
    @Singleton
    fun provideUserStoreManager(@UserStoragePreference dataStore: DataStore<Preferences>, api: UserApi): UserRepository {
        return UserRepositoryImpl(dataStore = dataStore, api = api)
    }

    @Provides
    @Singleton
    fun provideUserApi(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): UserApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UserApi::class.java)
    }

}