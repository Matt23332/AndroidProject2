package com.example.androidproject2.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

data class RevenueDataPoint(val date: Calendar, val revenue: Double)
data class ForecastDataPoint(val date: Calendar, val forecast: Double)

class FinancialTrendsViewModel : ViewModel() {
    private val _revenueData = MutableStateFlow<List<RevenueDataPoint>>(emptyList())
    val revenueData: StateFlow<List<RevenueDataPoint>> = _revenueData

    private val _forecastData = MutableStateFlow<List<ForecastDataPoint>>(emptyList())
    val forecastData: StateFlow<List<ForecastDataPoint>> = _forecastData

    val totalRevenue: Double
        get() = _revenueData.value.sumOf { it.revenue }

    init {
        loadFinancialData()
    }

    private fun loadFinancialData() {
        val calendar = Calendar.getInstance()

        _revenueData.value = listOf(
            RevenueDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, -30) }, 200.0),
            RevenueDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, -20) }, 225.0),
            RevenueDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, -10) }, 240.0),
            RevenueDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, 0) }, 270.0)
        )

        _forecastData.value = listOf(
            ForecastDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, 10) }, 300.0),
            ForecastDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, 20) }, 330.0),
            ForecastDataPoint(calendar.apply { add(Calendar.DAY_OF_YEAR, 30) }, 350.0)
        )
    }
}
