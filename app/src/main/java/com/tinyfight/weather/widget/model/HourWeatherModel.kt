package com.tinyfight.weather.widget.model

data class HourWeatherModel(
    val code: String,
    val hourly: List<Hourly>
)

data class Hourly(
    val temp: String,
    val text: String
)