package com.example.androidproject2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidproject2.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.androidproject2.data.Crop

@Composable
fun CropListScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val farmerId = currentUser?.uid ?: ""

    var crops by remember { mutableStateOf<List<Crop>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch crops from Firestore for the current user
    LaunchedEffect(farmerId) {
        if (farmerId.isNotEmpty()) {
            getUserCrops(farmerId, onSuccess = { fetchedCrops ->
                crops = fetchedCrops
            }, onFailure = { error ->
                errorMessage = error
            })
        }
    }

    // Displaying the crops
    Column(modifier = Modifier.padding(16.dp)) {
        errorMessage?.let {
            Text(text = "Error: $it", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
        }

        if (crops.isEmpty()) {
            Text(text = "No crops available.")
        } else {
            LazyColumn {
                items(crops) { crop ->
                    CropItem(crop)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (farmerId.isNotEmpty()) {
            // CropListScreen
            Button(
                onClick = { navController.navigate("crop_input") }, // Correct navigation route
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Crop")
            }


        } else {
            Text(text = "Please log in to add crops.")
        }

    }
}

fun getUserCrops(farmerId: String, onSuccess: (List<Crop>) -> Unit, onFailure: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("crops")
        .whereEqualTo("farmerId", farmerId)  // Query to filter crops by farmerId
        .get()
        .addOnSuccessListener { snapshot ->
            val crops = snapshot.documents.map { document ->
                Crop(
                    cropId = document.id, // Assuming Firestore document ID is used as cropId
                    name = document.getString("name") ?: "",
                    type = document.getString("type") ?: "",
                    quantity = document.getLong("quantity")?.toInt() ?: 0,
                    location = document.getString("location") ?: "",
                    farmerId = document.getString("farmerId") ?: "",
                    farmerName = document.getString("farmerName") ?: "",
                    cropImage = R.drawable.plant, // Provide a default image or fetch from Firestore if available
                    cropDescription = document.getString("description") ?: "No description available."
                )
            }
            onSuccess(crops)
        }
        .addOnFailureListener { e ->
            onFailure(e.message ?: "Unknown error")
        }
}

@Composable
fun CropItem(crop: Crop) {
    Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = crop.name, style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
            Text(text = "Type: ${crop.type}")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Location: ${crop.location}")
            Text(text = "Quantity: ${crop.quantity}")
        }
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

// Data class representing a crop
