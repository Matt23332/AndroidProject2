package com.example.androidproject2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidproject2.data.ImageSource
import com.example.androidproject2.model.Catalog
import com.example.androidproject2.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidproject2.model.FinancialTrendsViewModel

/*
@Composable
fun CatalogScreen() {
    val products = ImageSource().loadStock()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(products) { product ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Image(
                    painter = painterResource(id = product.imageResourceId),
                    contentDescription = stringResource(id = product.stringResourceId), // Fix here
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = stringResource(id = product.stringResourceId)) // Fix here
                    Text(text = product.price)
                }
            }
        }
    }
}

/*@Composable
fun CatalogScreen(modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing
                    .asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),) {
        CatalogList(catalogList = ImageSource().loadStock(),)
    }


}*/

@Composable
fun CatalogCard(
    catalog: Catalog,
    onClick: () -> Unit={},
    modifier: Modifier= Modifier
) {
    Card(modifier = modifier) {
        Column {
            Image(
                painter = painterResource(id = catalog.imageResourceId) ,
                contentDescription = stringResource( catalog.stringResourceId, catalog.price, catalog.ProductLink),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop

                )
            Text(
                text = LocalContext.current.getString(catalog.stringResourceId, catalog.price, catalog.ProductLink),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }

    }


}*/
@Composable
fun CatalogScreen(navController: NavController) {
    val products = ImageSource().loadStock()

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(products) { product ->
            CatalogCard(
                catalog = product,
                navController = navController
            )
        }
    }
}

@Composable
fun CatalogCard(
    catalog: Catalog,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .clickable {
            // Map the string resource ID to a cropId
            val cropId = when (catalog.imageResourceId) {
                R.drawable.beans -> "beans"
                R.drawable.rice -> "rice"
                R.drawable.bananas -> "bananas"
                R.drawable.oranges -> "oranges"
                R.drawable.grapes -> "grapes"
                R.drawable.maize -> "maize"
                R.drawable.potatoes -> "potatoes"
                R.drawable.cabbage -> "cabbage"
                R.drawable.sukuma_wiki -> "sukuma wiki"
                R.drawable.arrow_roots -> "arrow roots"
                R.drawable.wheat -> "wheat"
                else -> "unknown"
            }
            navController.navigate("product_details/$cropId")
            navController.navigate("product_details/${cropId.ifEmpty { "unknown" }}")
        }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = catalog.imageResourceId),
                contentDescription = stringResource(id = catalog.stringResourceId),
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = stringResource(id = catalog.stringResourceId))
                Text(text = catalog.price)
            }
        }
    }
}

/*
@Preview
@Composable
fun CatalogCardPreview(modifier: Modifier = Modifier) {
    CatalogCard(
        catalog = Catalog(R.string.crop1, R.drawable.beans,"250 Per kg", 250, "link" ))

}*/

@Composable
fun CatalogList(
    catalogList: List<Catalog>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val selectedCrop = remember { mutableStateOf<Catalog?>(null) } // Initialize selectedCrop as a state

    LazyColumn(modifier = modifier) {
        items(catalogList) { crop ->
            CatalogCard(catalog = crop, navController)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable(
            "product_details/{cropId}",
            arguments = listOf(navArgument("cropId") { type = NavType.StringType })
        ) { backStackEntry ->
            val cropId = backStackEntry.arguments?.getString("cropId") ?: ""
            // Get the ProductDetailsViewModel using the viewModel() function
            val viewModel: ProductDetailsViewModel = viewModel()
            ProductDetailsScreen(navController, cropId, viewModel)
        }
        composable("crop_input") { CropInputScreen(navController) }
        composable("financial_trends") {
            val viewModel: FinancialTrendsViewModel = viewModel() // Automatically creates or retrieves the ViewModel
            FinancialTrendsScreen(viewModel)
        }

        composable(
            route = "order_screen/{farmerId}/{cropId}",
            arguments = listOf(
                navArgument("farmerId") { type = NavType.StringType },
                navArgument("cropId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val farmerId = backStackEntry.arguments?.getString("farmerId") ?: ""
            val cropId = backStackEntry.arguments?.getString("cropId") ?: ""
            val viewModel: ProductDetailsViewModel = viewModel()  // Correct way to get ViewModel
            OrderScreen(navController, farmerId, cropId, viewModel)
        }
    }
}
