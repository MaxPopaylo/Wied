package ua.wied.data.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.repository.AuthRepositoryImpl
import ua.wied.domain.repository.AuthRepository
import ua.wied.domain.usecases.ClearUserDataUseCase
import ua.wied.domain.usecases.DeleteAccountUseCase
import ua.wied.domain.usecases.LogoutUseCase
import ua.wied.domain.usecases.SaveUserUseCase
import ua.wied.domain.usecases.SignInUseCase
import ua.wied.domain.usecases.SignUpUseCase
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(saveUserUseCase: SaveUserUseCase, clearUserDataUseCase: ClearUserDataUseCase): AuthRepository {
        return AuthRepositoryImpl(saveUserUseCase, clearUserDataUseCase)
    }

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

}