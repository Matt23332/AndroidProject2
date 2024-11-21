package com.example.androidproject2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun EditProfileScreen(navController: NavController) {
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val userId = currentUser?.uid ?: ""

    var username by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var profilePictureUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (userId.isNotEmpty()) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    profilePictureUrl = document.getString("profilePicture")
                }
        }
    }

    Surface(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    strokeWidth = 8.dp
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        isLoading = true

                        // Update Firestore user data
                        val updates = mapOf(
                            "name" to username,
                            "email" to email,
                        )
                        firestore.collection("users").document(userId).update(updates)
                            .addOnSuccessListener {
                                // Verify email update and update user profile display name
                                currentUser?.let { user ->
                                    user.verifyBeforeUpdateEmail(email)
                                    user.updateProfile(
                                        UserProfileChangeRequest.Builder() // Corrected line here
                                            .setDisplayName(username)
                                            .build()
                                    ).addOnCompleteListener { profileUpdateTask ->
                                        isLoading = false // Stop loading indicator after profile update completes

                                        if (profileUpdateTask.isSuccessful) {
                                            navController.popBackStack() // Navigate back after successful update
                                        } else {
                                            // Handle failure to update profile (optional)
                                        }
                                    }
                                } ?: run {
                                    isLoading = false // Handle case where currentUser is null (optional)
                                }
                            }
                            .addOnFailureListener {
                                isLoading = false
                            }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}