package com.example.androidproject2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Catalog : Screen("catalog", Icons.Default.Home, "Catalog")
    object History : Screen("history", Icons.Default.History, "History")
    object Trends : Screen("trends", Icons.AutoMirrored.Filled.TrendingUp, "Trends")
    object MyCrops : Screen("my crops", Icons.Default.Agriculture, "My Crops")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val bottomNavController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val userName = currentUser?.displayName ?: "User"
    val farmerId= currentUser?.uid

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
            composable(Screen.Trends.route) { TrendsScreen() }
            composable(Screen.MyCrops.route) { MyCropsScreen(navController) }
            composable(Screen.Profile.route) {
                val userId = currentUser?.uid
                val userEmail = currentUser?.email ?: "Email"
                var userRole by remember { mutableStateOf("Loading...") }

                val UserId = currentUser?.uid
                LaunchedEffect(UserId) {
                    if (userId != null) {
                        val firestore = FirebaseFirestore.getInstance()
                        firestore.collection("users").document(userId).get()
                            .addOnSuccessListener { document ->
                                userRole = document.getString("role") ?: "Unknown Role"
                            }
                            .addOnFailureListener {
                                userRole = "Error fetching the role"
                            }
                    } else {
                        userRole = "User not logged in"
                    }
                }
                ProfileScreen(
                    userName = userName,
                    userEmail = userEmail,
                    userRole = userRole
                )
            }
            composable(Screen.Profile.route) {EditProfileScreen(navController) }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val screens = listOf(
        Screen.Catalog,
        Screen.History,
        Screen.Trends,
        Screen.MyCrops,
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
fun TrendsScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Trends Screen",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MyCropsScreen(navController: NavController) {
    CropListScreen(navController = navController)
}

@Composable
fun ProfileScreen(
    userName: String,
    userEmail: String,
    userRole: String,
    modifier: Modifier = Modifier
) {
    val firestore = FirebaseFirestore.getInstance()
    val profilePicUrl = remember { mutableStateOf<String?>(null) }
    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    profilePicUrl.value = document.getString("profilePicture")
                    loading.value = false
                }
                .addOnFailureListener {
                    loading.value = false
                }
        } else {
            loading.value = false
        }
    }
    Surface(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
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
                    text = "Email: $userEmail",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "Role: $userRole",
                    style = MaterialTheme.typography.bodyLarge
                )
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Edit profile")
                }
            }
        }
    }
}
