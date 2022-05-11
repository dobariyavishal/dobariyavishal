package com.app.weatherapp.di

import android.app.Application
import android.content.Context
import com.app.weatherapp.BuildConfig
import com.app.weatherapp.network.ApiService
import com.app.weatherapp.network.NetworkConnect
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson, context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun okHttpClient(context: Context): OkHttpClient {
        val levelType: HttpLoggingInterceptor.Level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.apply {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.MINUTES)
            readTimeout(5, TimeUnit.MINUTES)
            writeTimeout(5, TimeUnit.MINUTES)
            addNetworkInterceptor(logging)
        }
        return httpClient.build()
    }

    @Provides
    fun networkConnect(context: Context): NetworkConnect {
        return NetworkConnect(context)
    }
}