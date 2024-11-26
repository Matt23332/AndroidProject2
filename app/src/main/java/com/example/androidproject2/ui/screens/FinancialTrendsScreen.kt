package com.example.androidproject2.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidproject2.model.FinancialTrendsViewModel
import com.example.androidproject2.model.ForecastDataPoint
import com.example.androidproject2.model.RevenueDataPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.text.*
/*
@Composable
fun FinancialTrendsScreen(viewModel: FinancialTrendsViewModel) {
    val revenueData = viewModel.revenueData.collectAsState().value
    val forecastData = viewModel.forecastData.collectAsState().value

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Total Revenue: Ksh ${viewModel.totalRevenue.formatToKsh()}")

        Text("Revenue Data:")
        revenueData.forEachIndexed { index, dataPoint ->
            // Pass a month offset based on the index (e.g., recent months are offset negatively)
            RevenueItem(dataPoint, monthOffset = -index)
        }

        Text("Forecast Data:")
        forecastData.forEachIndexed { index, dataPoint ->
            // Incrementally update forecast months positively
            ForecastItem(dataPoint, monthOffset = index + 1)
        }
    }
}
 */
@Composable
fun RevenueItem(revenueDataPoint: RevenueDataPoint, monthOffset: Int) {
    // Create a copy of the revenue date to avoid modifying the original
    val revenueDate = (revenueDataPoint.date.clone() as Calendar).apply {
        add(Calendar.MONTH, monthOffset)
    }

    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Display the updated revenue date
            Text("Date: ${SimpleDateFormat("yyyy-MM-dd").format(revenueDate.time)}")
            // Display revenue in Ksh with proper formatting
            Text("Revenue: Ksh ${revenueDataPoint.revenue.formatToKsh()}")
        }
    }
}

@Composable
fun ForecastItem(forecastDataPoint: ForecastDataPoint, monthOffset: Int) {
    // Create a copy of the forecast date to avoid modifying the original
    val forecastDate = (forecastDataPoint.date.clone() as Calendar).apply {
        add(Calendar.MONTH, monthOffset)
    }

    // Calculate the forecast amount by multiplying by 18% for each new forecast
    val forecastAmount = forecastDataPoint.forecast * Math.pow(1.18, monthOffset.toDouble())

    Card(modifier = Modifier.padding(bottom = 8.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Display the updated forecast date
            Text("Date: ${SimpleDateFormat("yyyy-MM-dd").format(forecastDate.time)}")
            // Display the forecasted amount with Ksh
            Text("Forecast: Ksh ${forecastAmount.formatToKsh()}")
        }
    }
}

// Extension function to format amounts in Kenyan Shillings
fun Double.formatToKsh(): String {
    return String.format("%,.2f", this)
}

@Preview(showBackground = true)
@Composable
fun PreviewFinancialTrends() {
    // Initialize the ViewModel correctly for preview
    val viewModel = FinancialTrendsViewModel()

    // Show the FinancialTrendsScreen composable with the ViewModel
    FinancialTrendsScreen(viewModel = viewModel)
}
