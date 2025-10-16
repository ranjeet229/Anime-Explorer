package com.animeExplorer.di

import com.animeExplorer.util.AppConstants
import com.animeExplorer.data.remote.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Koin module for network-related dependencies.
 * @author Udit
 */
val networkModule = module {
    
    /**
     * Provides Gson instance for JSON serialization/deserialization.
     * @return Configured Gson instance.
     * @author Udit
     */
    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }
    
    /**
     * Provides OkHttpClient with logging interceptor.
     * @return Configured OkHttpClient instance.
     * @author Udit
     */
    single<OkHttpClient> {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(AppConstants.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }
    
    /**
     * Provides Retrofit instance for API calls.
     * @return Configured Retrofit instance.
     * @author Udit
     */
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .build()
    }
    
    /**
     * Provides ApiService for making API calls.
     * @return ApiService instance.
     * @author Udit
     */
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}
