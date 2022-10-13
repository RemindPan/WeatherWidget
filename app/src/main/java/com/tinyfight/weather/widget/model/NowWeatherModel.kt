package com.tinyfight.weather.widget.model

data class NowWeatherModel(
    val code: String,
    val now: Now
)

data class Now(
    val temp: String,
    val text: String,
    val windDir: String,
    val windScale: String
)