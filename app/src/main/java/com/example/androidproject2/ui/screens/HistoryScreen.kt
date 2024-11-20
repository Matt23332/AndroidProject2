package com.example.androidproject2.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

    LaunchedEffect(farmerId) {
        if (farmerId == null) {
            Log.e("HistoryScreen", "Farmer ID is null. Cannot fetch sales.")
            return@LaunchedEffect
        }

        Log.d("HistoryScreen", "Fetching sales for farmerId: $farmerId")
        db.collection("sales")
            .whereEqualTo("farmerId", farmerId)
            .get()
            .addOnSuccessListener { result ->
                val sales = result.documents.mapNotNull { it.data }
                Log.d("HistoryScreen", "Fetched ${sales.size} sales.")
                salesState.value = sales
            }
            .addOnFailureListener { e ->
                Log.e("HistoryScreen", "Error fetching sales: ${e.message}")
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Sales History", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        if (salesState.value.isEmpty()) {
            Text(text = "No sales to display.", style = MaterialTheme.typography.bodyLarge)
        } else {
            salesState.value.forEach { sale ->
                SaleCard(sale)
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

    Card(modifier = Modifier.padding(8.dp)) {
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

