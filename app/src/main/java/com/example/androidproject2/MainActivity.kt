package com.example.androidproject2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.androidproject2.ui.screens.AppNavigation
import com.example.androidproject2.ui.CustomerScreens.CustomerScreen
import com.example.androidproject2.ui.screens.AppNavigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                val role = getUserRole(currentUser.uid)
                setContent {
                    val navController = rememberNavController()
                    when (role) {
                        "farmer" -> AppNavigation(navController)
                        "customer" -> CustomerScreen(navController)
                        else -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Unknown role. Redirecting to default page...",
                                Toast.LENGTH_SHORT
                            ).show()
                            AppNavigation(navController)
                        }
                    }
                }
            }
        }
    }
    private suspend fun getUserRole(userId: String): String {
        return try {
            val snapshot = firestore.collection("users").document(userId).get().await()
            snapshot.getString("role") ?: "unkown"
        } catch ( e: Exception) {
            Log.e("MainActivity", "Error fetching role: ${e.message}")
            "unkown"
        }
    }
}
