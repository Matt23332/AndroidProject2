package com.example.androidproject2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp

class SignUpActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase instances
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setContent {
            MaterialTheme {
                SignUpScreen(
                    onSignUpSuccess = {
                        Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    },
                    onNavigateToLogin = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var selectedRole by remember { mutableStateOf("customer") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Select Role", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            RadioButton(
                selected = selectedRole == "farmer",
                onClick = { selectedRole = "farmer" }
            )
            Text(
                text = "Farmer",
                modifier = Modifier.padding(start = 4.dp, top = 12.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = selectedRole == "customer",
                onClick = { selectedRole = "customer" }
            )
            Text(
                text = "Customer",
                modifier = Modifier.padding(start = 4.dp, top = 12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                signUpUser(
                    username.text,
                    email.text,
                    password.text,
                    confirmPassword.text,
                    selectedRole,
                    onSignUpSuccess
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIGN UP")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { onNavigateToLogin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? Login here.")
        }
    }
}

private fun signUpUser(
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    selectedrole: String,
    onSignUpSuccess: () -> Unit
) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    // Input validation
    if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
        Toast.makeText(firebaseAuth.app.applicationContext, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        return
    }

    if (password != confirmPassword) {
        Toast.makeText(firebaseAuth.app.applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
        return
    }

    // Create user with email and password
    firebaseAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                val user = firebaseAuth.currentUser
                val userId = user?.uid

                // Update display name in Firebase Auth
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username)
                    .build()

                user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                    if (profileTask.isSuccessful) {
                        // Create user data map
                        val userData = hashMapOf(
                            "userId" to userId,
                            "username" to username,
                            "email" to email,
                            "role" to selectedrole,
                            "createdAt" to Timestamp.now(),
                            "lastUpdated" to Timestamp.now()
                        )

                        // Save to main users collection
                        userId?.let { id ->
                            firestore.collection("users").document(id)
                                .set(userData)
                                .addOnSuccessListener {
                                    // Add role-specific data
                                    val roleSpecificData = userData.toMutableMap()

                                    if (selectedrole == "farmer") {
                                        roleSpecificData["name"] = username
                                        roleSpecificData["role"] = selectedrole
                                        roleSpecificData["email"] = email
                                        firestore.collection("farmers")
                                    } else {
                                        roleSpecificData["name"] = username
                                        roleSpecificData["email"] = email
                                        roleSpecificData["role"] = selectedrole
                                        firestore.collection("customers")
                                    }.document(id)
                                        .set(roleSpecificData)
                                        .addOnSuccessListener {
                                            onSignUpSuccess()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                firebaseAuth.app.applicationContext,
                                                "Error saving role data: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        firebaseAuth.app.applicationContext,
                                        "Error saving user data: ${e.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(
                            firebaseAuth.app.applicationContext,
                            "Error updating profile: ${profileTask.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    firebaseAuth.app.applicationContext,
                    "Sign up failed: ${authTask.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}