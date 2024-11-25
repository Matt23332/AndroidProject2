package com.example.myweather.model


import java.time.LocalDateTime

data class WeatherInfo(
    val temperature: Int,
    val weatherType: WeatherType,
    val time: LocalDateTime,
    val humidity: Int,
    val windSpeed: Int,
    val pressure: Int = 1013,
    val visibility: Int = 10,
    val uvIndex: Int = 0
)