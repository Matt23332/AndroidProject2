package com.example.myweather.ui.theme

import androidx.compose.material.icons.Icons

private val Icons.Rounded.Visibility: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.Speed: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.WaterDrop: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.DeviceThermostat: Any
    get() {TODO("Not yet implemented")
    }
private val Icons.Rounded.Air: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.AcUnit: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.Thunderstorm: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.Opacity: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.Grain: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.Cloud: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.CloudQueue: Any
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Rounded.WbSunny: Any
    get() {
        TODO("Not yet implemented")
    }

object WeatherIcons {
    // Weather condition icons
    val Sunny = Icons.Rounded.WbSunny
    val PartlyCloudy = Icons.Rounded.CloudQueue
    val Cloudy = Icons.Rounded.Cloud
    val Rainy = Icons.Rounded.Grain
    val HeavyRain = Icons.Rounded.Opacity
    val Stormy = Icons.Rounded.Thunderstorm
    val Windy
        get() = Icons.Rounded.Air
    val Foggy = Icons.Rounded.Cloud
    val Snow = Icons.Rounded.AcUnit
    val Hail = Icons.Rounded.AcUnit

    // Weather detail icons
    val Temperature = Icons.Rounded.DeviceThermostat
    val Humidity = Icons.Rounded.WaterDrop
    val WindSpeed = Icons.Rounded.Air
    val Pressure = Icons.Rounded.Speed
    val Visibility = Icons.Rounded.Visibility
    val UVIndex = Icons.Rounded.WbSunny
}