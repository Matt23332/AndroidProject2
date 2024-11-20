package com.example.androidproject2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun CropInputScreen(navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    val currentUser = auth.currentUser
    val farmerId = currentUser?.uid ?: ""
    val farmerName = currentUser?.displayName ?: "Unknown"

    var cropName by remember { mutableStateOf("") }
    var cropType by remember { mutableStateOf("") }
    var cropQuantity by remember { mutableStateOf("") }
    var cropLocation by remember { mutableStateOf("") }
    var cropDescription by remember { mutableStateOf("") }
    var cropImage by remember { mutableStateOf("") } // Placeholder for image URL or resource
    var isLoading by remember { mutableStateOf(false) }
    var toastMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Show Toast when toastMessage changes
    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
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
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cropDescription,
            onValueChange = { cropDescription = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = cropImage,
            onValueChange = { cropImage = it },
            label = { Text("Image URL / Resource ID") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (validateInputs(
                        cropName,
                        cropType,
                        cropQuantity,
                        cropLocation,
                        cropDescription,
                        cropImage
                    )
                ) {
                    isLoading = true
                    saveCropData(
                        db,
                        cropName,
                        cropType,
                        cropQuantity,
                        cropLocation,
                        cropDescription,
                        cropImage.toIntOrNull() ?: 0,
                        farmerId,
                        farmerName,
                        onSuccess = {
                            isLoading = false
                            navController.navigate("success") // Navigate to success page
                        },
                        onFailure = { errorMessage ->
                            isLoading = false
                            toastMessage = "Error: $errorMessage"
                        }
                    )
                } else {
                    toastMessage = "Please fill all fields correctly."
                }
            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Saving..." else "Submit")
        }
    }
}

fun saveCropData(
    db: FirebaseFirestore,
    name: String,
    type: String,
    quantity: String,
    location: String,
    description: String,
    image: Int,
    farmerId: String,
    farmerName: String,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    val parsedQuantity = quantity.toIntOrNull() ?: 0
    val crop = hashMapOf(
        "name" to name,
        "type" to type,
        "quantity" to parsedQuantity,
        "location" to location,
        "description" to description,
        "image" to image,
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

fun validateInputs(
    name: String,
    type: String,
    quantity: String,
    location: String,
    description: String,
    image: String
): Boolean {
    return name.isNotBlank() &&
            type.isNotBlank() &&
            quantity.toIntOrNull() != null &&
            location.isNotBlank() &&
            description.isNotBlank() &&
            image.isNotBlank()
}
