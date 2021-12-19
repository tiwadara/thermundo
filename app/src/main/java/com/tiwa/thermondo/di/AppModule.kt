package com.tiwa.thermondo.di

import com.tiwa.thermondo.data.api.NasaService
import com.tiwa.thermondo.data.constant.Constants
import com.tiwa.thermondo.data.constant.Constants.TIME_OUT
import com.tiwa.thermondo.data.repository.DefaultMarsImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDefaultShortLinkRepository(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
        nasaService: NasaService,
    ): DefaultMarsImageRepository {
        return DefaultMarsImageRepository(defaultDispatcher, nasaService)
    }

    @Singleton
    @Provides
    fun provideNasaService(): NasaService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
            .create(NasaService::class.java)
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        .build()

}

