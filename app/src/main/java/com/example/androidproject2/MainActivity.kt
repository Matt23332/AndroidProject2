package com.example.androidproject2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidproject2.ui.screens.CustomerNavigation
import com.example.androidproject2.ui.screens.DefaultNavigation
import com.example.androidproject2.ui.screens.FarmerNavigation
import com.example.androidproject2.ui.screens.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.local.Persistence

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser == null) {
            // Redirect to Login if not logged in
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            // Fetch the user's role and navigate accordingly
            val userId = currentUser.uid
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    val role = document.getString("role")
                    setContent {
                        val navController = rememberNavController()
                        if (role == "farmer") {
                            FarmerNavigation(navController) // Farmer Home Screen
                        } else if (role == "customer") {
                            CustomerNavigation(navController) // Customer Home Screen
                        } else {
                            // Default or fallback screen
                            DefaultNavigation(navController)
                        }
                    }
                }
                .addOnFailureListener {
                    Log.e("MainActivity", "Error fetching user role: ${it.message}")
                }
        }
    }
}


