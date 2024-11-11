package com.example.androidproject2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : ComponentActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        setContent {
            LoginScreen(
                onLoginSuccess = {
                    startActivity(Intent(this, MainActivity::class.java))
                },
                onNavigateToSignUp = {
                    startActivity(Intent(this, SignUpActivity::class.java))
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it},
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
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
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                loginUser(email.text, password.text, onLoginSuccess)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { showForgotPasswordDialog = true }) {
            Text("Forgot Password")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = { onNavigateToSignUp() }) {
            Text("Don't have an account? Sign up here.")
        }
    }

    if (showForgotPasswordDialog) {
        ForgotPasswordDialog(onDismiss = { showForgotPasswordDialog = false })
    }
}

fun loginUser(email: String, password: String, onLoginSuccess: () -> Unit) {
    if (email.isNotEmpty() && password.isNotEmpty()) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onLoginSuccess()
                } else {
                    Toast.makeText(
                        FirebaseAuth.getInstance().app.applicationContext,
                        "Error: ${it.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    } else {
        Toast.makeText(
            FirebaseAuth.getInstance().app.applicationContext,
            "Fields cannot be left empty.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
fun ForgotPasswordDialog(onDismiss: () -> Unit) {
    var email by remember { mutableStateOf(TextFieldValue("")) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Forgot Password") },
        text = {
            Column {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Enter your email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    sendPasswordResetEmail(email.text)
                    onDismiss()
                }
            ) {
                Text("Reset Password")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        modifier = Modifier.background(Color.White)
    )
}

fun sendPasswordResetEmail(email: String) {
    if (email.isEmpty()) {
        Toast.makeText(
            FirebaseAuth.getInstance().app.applicationContext,
            "Please enter your email address",
            Toast.LENGTH_SHORT
        ).show()
        return
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        Toast.makeText(
            FirebaseAuth.getInstance().app.applicationContext,
            "Please enter a valid email address",
            Toast.LENGTH_SHORT
        ).show()
        return
    }
    FirebaseAuth.getInstance()
        .sendPasswordResetEmail(email)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    FirebaseAuth.getInstance().app.applicationContext,
                    "Check your email for reset instructions",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    FirebaseAuth.getInstance().app.applicationContext,
                    "Error: ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}