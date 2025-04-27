package ua.wied.data.di.modules

import android.content.Context
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
import ua.wied.data.datasource.network.api.InstructionApi
import ua.wied.data.di.AuthenticatedClient
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.repository.InstructionRepositoryImpl
import ua.wied.domain.repository.InstructionRepository
import ua.wied.domain.usecases.DeleteElementUseCase
import ua.wied.domain.usecases.DeleteInstructionUseCase
import ua.wied.domain.usecases.CreateElementUseCase
import ua.wied.domain.usecases.CreateInstructionUseCase
import ua.wied.domain.usecases.UpdateElementUseCase
import ua.wied.domain.usecases.UpdateInstructionUseCase
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class InstructionModule {

    @Provides
    @Singleton
    fun provideSaveInstructionUseCase(
        repository: InstructionRepository
    ): CreateInstructionUseCase {
        return CreateInstructionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateInstructionUseCase(
        repository: InstructionRepository
    ): UpdateInstructionUseCase {
        return UpdateInstructionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteInstructionUseCase(
        repository: InstructionRepository
    ): DeleteInstructionUseCase {
        return DeleteInstructionUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveElementUseCase(
        repository: InstructionRepository
    ): CreateElementUseCase {
        return CreateElementUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateElementUseCase(
        repository: InstructionRepository
    ): UpdateElementUseCase {
        return UpdateElementUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteElementUseCase(
        repository: InstructionRepository
    ): DeleteElementUseCase {
        return DeleteElementUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInstructionRepository(
        api: InstructionApi,
        @ApplicationContext context: Context
    ): InstructionRepository {
        return InstructionRepositoryImpl(api, context)
    }

    @Provides
    @Singleton
    fun provideInstructionApi(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): InstructionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(InstructionApi::class.java)
    }
}