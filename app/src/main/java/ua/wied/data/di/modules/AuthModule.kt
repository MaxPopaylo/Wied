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
import ua.wied.data.UserPreferencesConstants.JWT_TOKEN_PREFERENCES
import ua.wied.data.datasource.network.api.AuthApi
import ua.wied.data.di.AuthClient
import ua.wied.data.di.JwtTokenPreference
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.repository.AuthRepositoryImpl
import ua.wied.data.repository.JwtTokenManagerImpl
import ua.wied.domain.repository.AuthRepository
import ua.wied.domain.repository.JwtTokenManager
import ua.wied.domain.usecases.ClearAllTokensUseCase
import ua.wied.domain.usecases.ClearUserDataUseCase
import ua.wied.domain.usecases.DeleteAccountUseCase
import ua.wied.domain.usecases.GetAccessJwtUseCase
import ua.wied.domain.usecases.LogoutUseCase
import ua.wied.domain.usecases.SaveAccessJwtUseCase
import ua.wied.domain.usecases.SaveUserUseCase
import ua.wied.domain.usecases.SignInUseCase
import ua.wied.domain.usecases.SignUpUseCase
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {

    //SIGN IN/UP
    @Provides
    fun provideSignInUseCase(repository: AuthRepository): SignInUseCase {
        return SignInUseCase(repository)
    }

    @Provides
    fun provideSignUpUseCase(repository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    fun provideLogoutUseCase(repository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Provides
    fun provideDeleteAccountUseCase(repository: AuthRepository): DeleteAccountUseCase {
        return DeleteAccountUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        saveUserUseCase: SaveUserUseCase,
        clearUserDataUseCase: ClearUserDataUseCase,
        saveAccessJwtUseCase: SaveAccessJwtUseCase,
        clearAllTokensUseCase: ClearAllTokensUseCase,
        authApi: AuthApi
    ): AuthRepository {
        return AuthRepositoryImpl(saveUserUseCase, saveAccessJwtUseCase, clearUserDataUseCase, clearAllTokensUseCase, authApi)
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        @AuthClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): AuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApi::class.java)
    }


    //JWT
    @Provides
    fun provideSaveAccessJwtUseCase(tokenManager: JwtTokenManager): SaveAccessJwtUseCase {
        return SaveAccessJwtUseCase(tokenManager)
    }

    @Provides
    fun provideGetAccessJwtUseCase(tokenManager: JwtTokenManager): GetAccessJwtUseCase {
        return GetAccessJwtUseCase(tokenManager)
    }

    @Provides
    fun provideClearAllTokensUseCase(tokenManager: JwtTokenManager): ClearAllTokensUseCase {
        return ClearAllTokensUseCase(tokenManager)
    }

    @Provides
    @Singleton
    fun provideUserStoreManager(@JwtTokenPreference dataStore: DataStore<Preferences>): JwtTokenManager {
        return JwtTokenManagerImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    @JwtTokenPreference
    fun provideJwtStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return StorageModule.createPreferenceDataStore(context, JWT_TOKEN_PREFERENCES)
    }

}