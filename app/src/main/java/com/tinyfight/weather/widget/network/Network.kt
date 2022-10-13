package com.tinyfight.weather.widget.network

import com.tinyfight.weather.widget.BuildConfig
import com.tinyfight.weather.widget.WEATHER_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .baseUrl(WEATHER_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()
}

private fun okHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        })
    }
    return okHttpClientBuilder.build()
}

