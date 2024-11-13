package com.example.androidproject2


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
//import androidx.media3.effect.Crop
import com.example.androidproject2.data.Crop
import com.google.firebase.firestore.FirebaseFirestore

class CropEntryActivity : ComponentActivity() {
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CropEntryScreen(onSave = { crop ->
                saveCropToFirestore(crop)
            })
        }
    }

    private fun saveCropToFirestore(crop: Crop) {
        firestore.collection("crops")
            .add(crop)
            .addOnSuccessListener {
                Toast.makeText(this, "Crop information saved successfully!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to save crop information: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
