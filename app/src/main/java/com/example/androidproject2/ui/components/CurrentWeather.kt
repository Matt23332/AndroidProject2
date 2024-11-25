package com.example.myweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.myweather.DateFormatter
import com.example.myweather.model.WeatherInfo
import com.example.myweather.ui.components.icon.WeatherIcon


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentWeather(weather: WeatherInfo) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "${weather.temperature}°",
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = weather.weatherType.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = DateFormatter.formatDateTime(weather.time),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        WeatherIcon(weather.weatherType)
    }
}