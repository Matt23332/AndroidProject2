package com.example.myweather.data.repository

import com.example.myweather.data.api.WeatherApiService
import com.example.myweather.model.WeatherResponse
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiService: WeatherApiService
) {
    suspend fun getWeatherData(city: String): WeatherResponse {
        return apiService.getCurrentWeather(
            cityName = city,
            apiKey = "7ecc67ad2f7f4f25809205844242011"
        )
    }
}