package com.example.androidproject2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidproject2.data.ImageSource
import com.example.androidproject2.model.Catalog
import com.example.androidproject2.R

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
    //onClick: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth()
        .clickable {
        // Navigate to product details with the crop name as a parameter
        navController.navigate("product_details/${catalog.stringResourceId}")
    })
    {
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
        composable("home") { HomeScreen() }  // Home screen as entry point
        composable("catalog") { CatalogScreen(navController) }
        composable("product_details/{cropId}") { backStackEntry ->
            val cropId = backStackEntry.arguments?.getString("cropId") ?: ""
            val viewModel = ProductDetailsViewModel()
            ProductDetailsScreen(navController, cropId, viewModel)
        }
    }
}
