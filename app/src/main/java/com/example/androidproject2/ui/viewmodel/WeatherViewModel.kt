package com.example.myweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.data.repository.WeatherRepository
import com.example.myweather.ui.state.WeatherUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val weatherState: StateFlow<WeatherUiState> = _weatherState

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                _weatherState.value = WeatherUiState.Loading
                val weatherData = repository.getWeatherData(city)
                _weatherState.value = WeatherUiState.Success(weatherData)
            } catch (e: Exception) {
                _weatherState.value = WeatherUiState.Error("Failed to fetch weather data")
            }
        }
    }
}