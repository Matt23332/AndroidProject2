package com.example.myweather.ui.components.icon

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.myweather.model.WeatherType

@Composable
fun WeatherIcon(
    weatherType: WeatherType,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary,
    contentDescription: String? = weatherType.description
) {
    Icon(
        imageVector = weatherType.icon as ImageVector,
        contentDescription = contentDescription,
        modifier = modifier,
        tint = tint
    )
}