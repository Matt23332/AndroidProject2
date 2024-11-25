package com.example.androidproject2.ui.CustomerScreens

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.androidproject2.LoginActivity
import com.example.androidproject2.ui.screens.CatalogScreen
import com.example.androidproject2.ui.screens.CropListScreen
import com.example.androidproject2.ui.screens.EditProfileScreen
import com.example.androidproject2.ui.screens.HistoryScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import androidx.compose.ui.platform.LocalContext

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Catalog : Screen("catalog", Icons.Default.Home, "Catalog")
    object History : Screen("history", Icons.Default.History, "History")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userName = currentUser?.displayName ?: "User"

    Scaffold(
        topBar = { TopAppBar(title = { Text("Welcome, $userName") }) },
        bottomBar = { BottomNavBar(navController = bottomNavController) }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Screen.Catalog.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Catalog.route) { CatalogScreen(navController) }
            composable(Screen.History.route) { HistoryScreen(navController) }
            composable(Screen.Profile.route) { ProfileScreen(navController) }
            composable("EditProfileScreen") { EditProfileScreen(navController) }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Catalog,
        Screen.History,
        Screen.Profile
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, contentDescription = screen.label) },
                label = { Text(screen.label) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun ProfileScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = android.content.ContextWrapper(LocalContext.current)
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    val userEmail = currentUser?.email
    var userName by remember { mutableStateOf(currentUser?.displayName ?: "") }
    var userRole by remember { mutableStateOf("Loading...") }
    val profilePicUrl = remember { mutableStateOf<String?>(null) }
    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        if (userEmail != null) {
            firestore.collection("users")
                .whereEqualTo("email", userEmail)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val document = querySnapshot.documents.firstOrNull()
                    if (document != null) {
                        userName = document.getString("name") ?: ""
                        userRole = document.getString("role") ?: "Unknown Role"
                        profilePicUrl.value = document.getString("profilePicture")
                    }
                    loading.value = false
                }
                .addOnFailureListener {
                    userRole = "Error fetching the role"
                    loading.value = false
                }
        } else {
            userRole = "User not logged in"
            loading.value = false
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    strokeWidth = 8.dp
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Name: $userName",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Email: ${userEmail ?: "N/A"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Role: $userRole",
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = { navController.navigate("EditProfileScreen") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit Profile")
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
