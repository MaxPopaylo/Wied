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
import ua.wied.data.di.AuthenticatedClient
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import javax.inject.Singleton
import ua.wied.data.datasource.network.api.AiInstructionApi
import ua.wied.data.datasource.network.api.EvaluationApi
import ua.wied.data.repository.AiInstructionRepositoryImpl
import ua.wied.data.repository.EvaluationRepositoryImpl
import ua.wied.domain.repository.AiInstructionRepository
import ua.wied.domain.repository.EvaluationRepository

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class AiInstructionModule {

    @Provides
    @Singleton
    fun provideAiInstructionRepository(
        api: AiInstructionApi
    ): AiInstructionRepository {
        return AiInstructionRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideAiInstructionApi(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): AiInstructionApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AiInstructionApi::class.java)
    }

}