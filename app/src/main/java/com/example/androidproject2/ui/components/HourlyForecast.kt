package com.example.myweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.myweather.model.WeatherInfo


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyForecast(forecast: List<WeatherInfo>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(forecast) { hourForecast ->
            HourlyForecastItem(hourForecast)
        }
    }
}
