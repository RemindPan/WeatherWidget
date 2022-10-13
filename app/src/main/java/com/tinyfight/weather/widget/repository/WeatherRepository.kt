package com.tinyfight.weather.widget.repository

import com.tinyfight.weather.widget.XINBEI_LOCATION_ID
import com.tinyfight.weather.widget.model.DayWeatherModel
import com.tinyfight.weather.widget.model.HourWeatherModel
import com.tinyfight.weather.widget.model.NowWeatherModel
import com.tinyfight.weather.widget.network.Result
import com.tinyfight.weather.widget.network.WeatherApi
import com.tinyfight.weather.widget.network.safeApiCall
import com.tinyfight.weather.widget.network.safeFlowApiCall
import kotlinx.coroutines.flow.Flow

class WeatherRepository {
    suspend fun requestNowWeather(): Result<NowWeatherModel> = safeApiCall {
        WeatherApi.api.getNowWeather(locationId = XINBEI_LOCATION_ID)
    }

    suspend fun requestHourlyWeather(): Result<HourWeatherModel> = safeApiCall {
        WeatherApi.api.getHourWeather(locationId = XINBEI_LOCATION_ID)
    }

    suspend fun requestDailyWeather(): Result<DayWeatherModel> = safeApiCall {
        WeatherApi.api.getDayWeather(locationId = XINBEI_LOCATION_ID)
    }

    suspend fun requestNowWeather(locationId: String): Flow<NowWeatherModel> = safeFlowApiCall {
        WeatherApi.api.getNowWeather(locationId = locationId)
    }

    suspend fun requestHourlyWeather(locationId: String): Flow<HourWeatherModel> = safeFlowApiCall {
        WeatherApi.api.getHourWeather(locationId = locationId)
    }

    suspend fun requestDailyWeather(locationId: String): Flow<DayWeatherModel> = safeFlowApiCall {
        WeatherApi.api.getDayWeather(locationId = locationId)
    }
}