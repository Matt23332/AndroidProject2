package com.example.myweather.ui.components.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myweather.model.WeatherInfo
import com.example.myweather.ui.components.items.WeatherDetailItem
import com.example.myweather.ui.theme.WeatherIcons

@Composable
fun WeatherDetails(
    weather: WeatherInfo,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailItem(
            icon = WeatherIcons.Humidity,
            value = "${weather.humidity}%",
            label = "Humidity",
            contentDescription = "Humidity percentage is ${weather.humidity}%"
        )
        WeatherDetailItem(
            icon = WeatherIcons.WindSpeed,
            value = "${weather.windSpeed} km/h",
            label = "Wind Speed",
            contentDescription = "Wind speed is ${weather.windSpeed} kilometers per hour"
        )
        WeatherDetailItem(
            icon = WeatherIcons.Pressure,
            value = "${weather.pressure} hPa",
            label = "Pressure",
            contentDescription = "Pressure is ${weather.pressure} hectopascals"
        )
        WeatherDetailItem(
            icon = WeatherIcons.UVIndex,
            value = "${weather.uvIndex}",
            label = "UV Index",
            contentDescription = "UV Index is ${weather.uvIndex}"
        )
    }
}