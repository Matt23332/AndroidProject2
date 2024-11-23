package com.example.myweather.model

import com.example.myweather.ui.theme.WeatherIcons


enum class WeatherType(
    val icon: Any,
    val description: String
) {
    CLEAR_SKY(
        icon = WeatherIcons.Sunny,
        description = "Clear Sky"
    ),
    PARTLY_CLOUDY(
        icon = WeatherIcons.PartlyCloudy,
        description = "Partly Cloudy"
    ),
    CLOUDY(
        icon = WeatherIcons.Cloudy,
        description = "Cloudy"
    ),
    LIGHT_RAIN(
        icon = WeatherIcons.Rainy,
        description = "Light Rain"
    ),
    HEAVY_RAIN(
        icon = WeatherIcons.HeavyRain,
        description = "Heavy Rain"
    ),
    STORMY(
        icon = WeatherIcons.Stormy,
        description = "Thunderstorm"
    ),
    WINDY(
        icon = WeatherIcons.Windy,
        description = "Windy"
    ),
    FOGGY(
        icon = WeatherIcons.Foggy,
        description = "Foggy"
    ),
    SNOW(
        icon = WeatherIcons.Snow,
        description = "Snow"
    ),
    HAIL(
        icon = WeatherIcons.Hail,
        description = "Hail"
    );

    companion object {
        fun fromApiValue(apiValue: String): WeatherType {
            return when (apiValue.lowercase()) {
                "01d", "01n" -> CLEAR_SKY
                "02d", "02n" -> PARTLY_CLOUDY
                "03d", "03n", "04d", "04n" -> CLOUDY
                "09d", "09n" -> LIGHT_RAIN
                "10d", "10n" -> HEAVY_RAIN
                "11d", "11n" -> STORMY
                "13d", "13n" -> SNOW
                "50d", "50n" -> FOGGY
                else -> CLEAR_SKY
            }
        }
    }
}