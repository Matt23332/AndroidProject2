package com.example.androidproject2.ui.screens

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.androidproject2.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun EditProfileScreen(navController: NavController) {
    val context = android.content.ContextWrapper(LocalContext.current)
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val userEmail = currentUser?.email ?: ""

    var username by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var profilePictureUrl by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (userEmail.isNotEmpty()) {
            firestore.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val document = querySnapshot.documents.firstOrNull()
                    if (document != null) {
                        username = document.getString("name") ?: ""
                        profilePictureUrl = document.getString("profilePicture")
                    }
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

                        firestore.collection("users")
                            .whereEqualTo("email", userEmail)
                            .get()
                            .addOnSuccessListener { querySnapshot ->
                                val document = querySnapshot.documents.firstOrNull()
                                if (document != null) {
                                    val documentId = document.id
                                    val updates = mapOf(
                                        "name" to username,
                                        "email" to email,
                                    )
                                    firestore.collection("users").document(documentId)
                                        .update(updates)
                                        .addOnSuccessListener {
                                            currentUser?.let { user ->
                                                user.verifyBeforeUpdateEmail(email)
                                                user.updateProfile(
                                                    UserProfileChangeRequest.Builder()
                                                        .setDisplayName(username)
                                                        .build()
                                                ).addOnCompleteListener { profileUpdateTask ->
                                                    isLoading = false

                                                    if (profileUpdateTask.isSuccessful) {
                                                        navController.popBackStack()
                                                    } else {

                                                    }
                                                }
                                            } ?: run {
                                                isLoading = false
                                            }
                                        }
                                        .addOnFailureListener {
                                            isLoading = false
                                        }
                                } else {
                                    isLoading = false
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
                Button(
                    onClick = {
                        auth.signOut()
                        val intent = Intent(context, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        }
    }
}