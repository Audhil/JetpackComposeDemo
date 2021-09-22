package com.example.xjpackcompose.di

import com.example.xjpackcompose.data.remote.PokeApi
import com.example.xjpackcompose.data.repository.RabbitRepositoryImpl
import com.example.xjpackcompose.domain.repository.PokemonRepository
import com.example.xjpackcompose.domain.repository.RabbitRepository
import com.example.xjpackcompose.util.Constants.BASE_URL
import com.example.xjpackcompose.util.showAPIVLog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)

    @Provides
    @Singleton
    fun giveOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            //  logger interceptor
            .addInterceptor(HttpLoggingInterceptor {
                showAPIVLog { "yup: api logging:$it" }
            }.apply {
                level = if (true)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providePokeApi(
        okHttpClient: OkHttpClient
    ): PokeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApi::class.java)
    }

    @Provides
    @Singleton
    fun giveRabbitRepository(api: PokeApi): RabbitRepository = RabbitRepositoryImpl(api = api)
}