package com.example.androidproject2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth


@Composable
fun CropInputScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()  // Firebase Authentication instance

    val currentUser = auth.currentUser
    var cropName by remember { mutableStateOf("") }
    var cropType by remember { mutableStateOf("") }
    var cropQuantity by remember { mutableStateOf("") }
    var cropLocation by remember { mutableStateOf("") }
    var showToast by remember { mutableStateOf("") }
    val context = LocalContext.current

    val farmerId = currentUser?.uid ?: ""
    val farmerName = currentUser?.displayName ?: "Unknown"

    // LaunchedEffect to display the Toast when showToast is updated
    LaunchedEffect(showToast) {
        // Use LocalContext.current to get the context
        if (showToast.isNotEmpty()) {
            Toast.makeText(context, showToast, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = cropName,
            onValueChange = { cropName = it },
            label = { Text("Crop Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cropType,
            onValueChange = { cropType = it },
            label = { Text("Crop Type") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cropQuantity,
            onValueChange = { cropQuantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cropLocation,
            onValueChange = { cropLocation = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            saveCropData(
                db, cropName, cropType, cropQuantity, cropLocation, farmerId, farmerName,
                onSuccess = {
                    navController.navigate("success") // Navigate to success page
                },
                onFailure = { errorMessage ->
                    showToast = "Error: $errorMessage"
                }
            )
        }) {
            Text("Submit")
        }
    }
}

fun saveCropData(
    db: FirebaseFirestore,
    name: String,
    type: String,
    quantity: String,
    location: String,
    farmerId: String,
    farmerName: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val crop = hashMapOf(
        "name" to name,
        "type" to type,
        "quantity" to quantity,
        "location" to location,
        "farmerId" to farmerId,
        "farmerName" to farmerName
    )

    db.collection("crops")
        .add(crop)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { e ->
            onFailure(e.message ?: "Unknown error")
        }
}
