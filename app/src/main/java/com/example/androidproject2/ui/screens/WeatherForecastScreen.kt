package com.example.androidproject2.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myweather.model.WeatherInfo
import com.example.myweather.ui.components.CurrentWeather
import com.example.myweather.ui.components.HourlyForecast
import com.example.myweather.ui.components.WeatherDetails


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecastScreen(
    currentWeather: WeatherInfo,
    hourlyForecast: List<WeatherInfo>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            CurrentWeather(currentWeather)

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            WeatherDetails(currentWeather)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Hourly Forecast",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            HourlyForecast(hourlyForecast)
        }
    }
}
