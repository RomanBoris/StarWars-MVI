package com.pobezhkin.starwars_mvi.di

import com.pobezhkin.starwars_mvi.heroes.data.remote.StarApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Тот же стек, что был в HeroesFactory урока 12 (а до неё — в старом
// StarWarsApp-master/di/NetworkModule.kt), просто теперь его строит Dagger, а не руки.
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        )
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://swapi.dev/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesStarApiService(retrofit: Retrofit): StarApiService =
        retrofit.create(StarApiService::class.java)
}
