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
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    object Catalog : Screen("catalog", Icons.Default.Home, "Catalog")
    object History : Screen("history", Icons.Default.History, "History")
    object Trends : Screen("trends", Icons.AutoMirrored.Filled.TrendingUp, "Trends")
    object MyCrops : Screen("my crops", Icons.Default.Agriculture, "My Crops")
    object Profile : Screen("profile", Icons.Default.Person, "Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val currentUser = FirebaseAuth.getInstance().currentUser
    val userName = currentUser?.displayName ?: currentUser
    val userEmail = currentUser?.email ?: currentUser

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Welcome, $userName",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Catalog.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Catalog.route) {
                CatalogScreen(
                    userName = userName.toString(),
                    recommendItems = listOf("Corn", "Wheat", "Potatoes")
                )
            }
            composable(Screen.History.route) {
                HistoryScreen(
                    userHistory = listOf(
                        "Planted corn on 2024-11-10",
                        "Harvested maize on 2024-11-20"
                    )
                )
            }
            composable(Screen.Trends.route) {
                TrendsScreen(
                    trendData = mapOf(
                        "Corn prices" to "Up 5%",
                        "Maize harvest" to "Steady",
                        "Potatoes demand" to "high"
                    )
                )
            }
            composable(Screen.MyCrops.route) {
                MyCropsScreen(
                    navController = navController,
                    crops = listOf(
                        Crop(name = "Corn", status = "Planted", progress = 50),
                        Crop(name = "Maize", status = "Planting", progress = 30)
                    )
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    userName = userName.toString(),
                    userEmail = userEmail.toString()
                )
            }
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
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Composable
fun CatalogScreen(userName: String, recommendItems: List<String>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Hello, $userName! Here are some recommendations:", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        recommendItems.forEach { item ->
            Text(text =  item, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun HistoryScreen(userHistory: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your activity history:",
            style = MaterialTheme.typography.headlineSmall
        )
        userHistory.forEach { action ->
            Text(
                text = action,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun TrendsScreen(trendData: Map<String, String>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Market trends", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        trendData.forEach{ (key, value) ->
            Text(text = "$key: $value", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

data class Crop(val name: String, val status: String, val progress: Int)

@Composable
fun MyCropsScreen(navController: NavController, crops: List<Crop>) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "My crops:", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        crops.forEach { crop ->
            Text(
                text = "${crop.name}: ${crop.status} (${crop.progress}%)",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProfileScreen(userName: String, userEmail: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = userEmail,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}