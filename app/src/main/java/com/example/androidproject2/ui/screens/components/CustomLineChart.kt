package com.example.androidproject2.ui.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

data class ChartData(val x: Float, val y: Float)

@Composable
fun CustomLineChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color.Blue,
    pointColor: Color = Color.Red
) {
    Card(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val width = constraints.maxWidth.toFloat()
            val height = constraints.maxHeight.toFloat()

            val maxX = data.maxOf { it.x }
            val maxY = data.maxOf { it.y }

            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw line
                val path = Path()
                data.forEachIndexed { index, point ->
                    val x = (point.x / maxX) * width
                    val y = height - (point.y / maxY) * height

                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }
                drawPath(path, color = lineColor, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4f))

                // Draw points
                data.forEach { point ->
                    val x = (point.x / maxX) * width
                    val y = height - (point.y / maxY) * height
                    drawCircle(color = pointColor, radius = 8f, center = Offset(x, y))
                }
            }
        }
    }
}

@Composable
fun FinancialTrendsScreen() {
    val sampleData = listOf(
        ChartData(1f, 100f),
        ChartData(2f, 200f),
        ChartData(3f, 150f),
        ChartData(4f, 300f),
        ChartData(5f, 250f)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Financial Trends",
            style = MaterialTheme.typography.headlineMedium
        )

        Text("Revenue Over Time", style = MaterialTheme.typography.titleLarge)
        CustomLineChart(data = sampleData)

        Text("Forecasting Trends", style = MaterialTheme.typography.titleLarge)
        CustomLineChart(data = sampleData.map { ChartData(it.x, it.y * 1.2f) })
    }
}
