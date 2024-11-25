package com.example.myweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweather.model.WeatherInfo
import com.example.myweather.ui.theme.WeatherIcons


@Composable
fun WeatherDetails(weather: WeatherInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailItem(
            icon = WeatherIcons.Humidity,
            value = "${weather.humidity}%",
            label = "Humidity",
            contentDescription = "Humidity percentage is ${weather.humidity}%"
        )

        // Wind Speed Detail
        WeatherDetailItem(
            icon = WeatherIcons.WindSpeed,
            value = "${weather.windSpeed} km/h",
            label = "Wind Speed",
            contentDescription = "Wind speed is ${weather.windSpeed} kilometers per hour"
        )
    }
}