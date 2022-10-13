package com.tinyfight.weather.widget.model

data class DayWeatherModel(
    val code: String,
    val daily: List<Daily>
)

data class Daily(
    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val tempMax: String,
    val tempMin: String,
    val textDay: String,
    val textNight: String,
    val windDirDay: String,
    val windScaleDay: String,
    val windDirNight: String,
    val windScaleNight: String
)
