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
import ua.wied.data.datasource.network.api.ReportApi
import ua.wied.data.di.AuthenticatedClient
import ua.wied.data.di.NetworkModule
import ua.wied.data.di.StorageModule
import ua.wied.data.repository.ReportRepositoryImpl
import ua.wied.domain.repository.ReportRepository
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, StorageModule::class])
@InstallIn(SingletonComponent::class)
class ReportModule {

    @Provides
    @Singleton
    fun provideReportRepository(
        api: ReportApi,
        @ApplicationContext context: Context
    ): ReportRepository {
        return ReportRepositoryImpl(api, context)
    }

    @Provides
    @Singleton
    fun provideReportApi(
        @AuthenticatedClient okHttpClient: OkHttpClient,
        moshi: Moshi
    ): ReportApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ReportApi::class.java)
    }

}