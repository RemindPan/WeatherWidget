package com.tinyfight.weather.widget.network

import com.tinyfight.weather.widget.WEATHER_REQUEST_KEY
import com.tinyfight.weather.widget.model.DayWeatherModel
import com.tinyfight.weather.widget.model.HourWeatherModel
import com.tinyfight.weather.widget.model.NowWeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("now")
    suspend fun getNowWeather(
        @Query("location") locationId: String,
        @Query("key") key: String = WEATHER_REQUEST_KEY
    ): NowWeatherModel

    @GET("24h")
    suspend fun getHourWeather(
        @Query("location") locationId: String,
        @Query("key") key: String = WEATHER_REQUEST_KEY
    ): HourWeatherModel

    @GET("7d")
    suspend fun getDayWeather(
        @Query("location") locationId: String,
        @Query("key") key: String = WEATHER_REQUEST_KEY
    ): DayWeatherModel

    companion object {
        val api: WeatherApi = retrofit.create(WeatherApi::class.java)
    }
}