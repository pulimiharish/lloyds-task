package com.lbg.data.di

import com.lbg.data.BuildConfig
import com.lbg.data.api.FilmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url

            val newHttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build()

            chain.proceed(newRequest)
        }
        .addInterceptor(logger)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideFilmService(retrofit: Retrofit): FilmService {
        return retrofit.create(FilmService::class.java)
    }
}