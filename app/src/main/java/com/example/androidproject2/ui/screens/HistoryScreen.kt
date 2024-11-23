package com.example.androidproject2.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreen(
    navController: NavController
) {
    val db = FirebaseFirestore.getInstance()
   // val currentUser = FirebaseAuth.getInstance().currentUser
    val farmerId = FirebaseAuth.getInstance().currentUser?.uid
    Log.d("HistoryScreen", "Current farmerId: $farmerId")

    val salesState = remember { mutableStateOf<List<Map<String, Any>>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    val errorState = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(farmerId) {
        if (farmerId == null) {
            Log.e("HistoryScreen", "Farmer ID is null. Cannot fetch sales.")
            errorState.value = "Unable to fetch sales."
            isLoading.value = false
            return@LaunchedEffect
        }

        Log.d("HistoryScreen", "Fetching sales for farmerId: $farmerId")
        db.collection("sales")
            .whereEqualTo("farmerId", farmerId)
            .get()
            .addOnSuccessListener { result ->
                val sales = result.documents.mapNotNull { it.data }
                Log.d("HistoryScreen", "Fetched ${sales.size} sales.")
                result.documents.forEach { document ->
                    Log.d("HistoryScreen", "Sale: ${document.data}")
                }
                salesState.value = sales
                isLoading.value = false
            }
            .addOnFailureListener { e ->
                Log.e("HistoryScreen", "Error fetching sales: ${e.message}")
                errorState.value = "Error fetching sales: ${e.message}"
                isLoading.value = false
            }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text = "Sales History", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading.value -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorState.value != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = errorState.value ?: "An error occurred",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            salesState.value.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No sales to display.",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                salesState.value.forEach { sale ->
                    SaleCard(sale)
                }
            }
        }
    }
}


@Composable
fun SaleCard(sale: Map<String, Any>) {
    val timestamp = sale["timestamp"] as? Long
    val formattedDate = timestamp?.let {
        SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(it)
    } ?: "Unknown Date"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Crop: ${sale["cropId"] ?: "Unknown"}")
            Text(text = "Farmer: ${sale["farmerName"] ?: "Unknown"}")
            Text(text = "Customer: ${sale["customerName"] ?: "Unknown"}")
            Text(text = "Quantity: ${sale["quantity"] ?: 0} kg")
            Text(text = "Delivery Address: ${sale["deliveryAddress"] ?: "Unknown"}")
            Text(text = "Date Sold: $formattedDate")
        }
    }
}

