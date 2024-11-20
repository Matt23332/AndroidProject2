package com.example.androidproject2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        setContent {
            SignUpScreen (
                onSignUpSuccess = {
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

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    var userRole by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
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
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = userRole,
            onValueChange = { userRole = it },
            label = { Text("Role") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                signUpUser(email.text, password.text, confirmPassword.text, userRole.text, onSignUpSuccess)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIGN UP")
        }

        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { onNavigateToLogin() }) {
            Text("Already have an account? Login here.")
        }
    }
}

fun signUpUser(email: String, password: String, confirmPassword: String, role: String, onSignupSuccess: () -> Unit) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && role.isNotEmpty()) {
        if (password == confirmPassword) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    val userId = firebaseAuth.currentUser?.uid
                    val userData = hashMapOf(
                        "email" to email,
                        "role" to role
                    )

                    // Add user to the main 'users' collection
                    userId?.let { id ->
                        firestore.collection("users").document(id).set(userData).addOnSuccessListener {
                            // Add user to either 'farmers' or 'customers' collection based on role
                            val collectionName = if (role.equals("farmer", ignoreCase = true)) "farmers" else "customers"
                            firestore.collection(collectionName).document(id).set(userData)
                                .addOnSuccessListener {
                                    onSignupSuccess()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(firebaseAuth.app.applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }.addOnFailureListener { e ->
                            Toast.makeText(firebaseAuth.app.applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(firebaseAuth.app.applicationContext, "Error: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(firebaseAuth.app.applicationContext, "Passwords do not match", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(firebaseAuth.app.applicationContext, "Fields cannot be left empty", Toast.LENGTH_SHORT).show()
    }
}