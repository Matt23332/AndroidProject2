package com.example.androidproject2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.androidproject2.ui.screens.AppNavigation
import com.example.androidproject2.ui.screens.HomeScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.local.Persistence

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        //firebaseAuth.setPersistence(Persistence.PERSISTENT) // Ensures the session is maintained across app restarts.

        // Check if the user is already logged in
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            // If not logged in, navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            // If logged in, show the HomeScreen
            setContent {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}

