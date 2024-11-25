package com.example.myweather.ui.components
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myweather.DateFormatter
import com.example.myweather.model.WeatherInfo
import com.example.myweather.ui.components.icon.WeatherIcon


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyForecastItem(weather: WeatherInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Text(
            text = DateFormatter.formatTime(weather.time),
            style = MaterialTheme.typography.bodyMedium
        )
        WeatherIcon(
            weatherType = weather.weatherType,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = "${weather.temperature}°",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}