package ua.wied.data.di.modules

import com.skydoves.sandwich.retrofit.adapters.ApiResponseCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ua.wied.data.NetworkKeys.BASE_URL
import ua.wied.data.datasource.network.api.FolderApi
import ua.wied.data.di.AuthenticatedClient
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.repository.FolderRepositoryImpl
import ua.wied.domain.repository.FolderRepository
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.domain.usecases.GetInstructionWithReportCountFoldersUseCase
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class FolderModule {

    @Provides
    fun provideGetInstructionFoldersUseCase(
        repository: FolderRepository
    ): GetInstructionFoldersUseCase {
        return GetInstructionFoldersUseCase(repository)
    }

    @Provides
    fun provideGetInstructionWithReportCountFoldersUseCase(
        repository: FolderRepository
    ): GetInstructionWithReportCountFoldersUseCase {
        return GetInstructionWithReportCountFoldersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFolderRepository(
        api: FolderApi
    ): FolderRepository {
        return FolderRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideFolderApi(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): FolderApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(FolderApi::class.java)
    }

}