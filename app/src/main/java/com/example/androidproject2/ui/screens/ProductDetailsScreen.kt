package com.example.androidproject2.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

import com.example.androidproject2.R
import com.example.androidproject2.data.Crop
import com.example.androidproject2.data.OrderData
import com.example.androidproject2.data.OrderState
import com.example.androidproject2.data.ProductDescriptionData
import com.example.androidproject2.data.ProductFlavourData
import com.example.androidproject2.data.ProductFlavourState
import com.example.androidproject2.data.ProductNutritionData
import com.example.androidproject2.data.ProductNutritionState
import com.example.androidproject2.data.ProductPreviewState
import com.example.androidproject2.ui.screens.components.FlavourSection
import com.example.androidproject2.ui.screens.components.OrderActionBar
import com.example.androidproject2.ui.screens.components.ProductDescriptionSection
import com.example.androidproject2.ui.screens.components.ProductNutritionSection
import com.example.androidproject2.ui.screens.components.ProductPreviewSection
import com.google.firebase.firestore.FirebaseFirestore


/*
fun ProductDetailsScreen(
    modifier: Modifier = Modifier,
    productPreviewState: ProductPreviewState =ProductPreviewState(),
    productFlavours:  List<ProductFlavourState> = ProductFlavourData,
    productNutritionState: ProductNutritionState = ProductNutritionData,
    productDescription: String = ProductDescriptionData,
    orderState: OrderState = OrderData,
    onAddItemClicked: () -> Unit = {}, // Default empty lambda function
    onRemoveItemClicked: () -> Unit = {}, // Default empty lambda function
    onCheckOutItemClicked: () -> Unit = {} // Default empty lambda function

){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter ){
        Content(
            productPreviewState = productPreviewState,
            productFlavours = productFlavours,
            productNutritionState = productNutritionState,
            productDescription = productDescription
        )
        OrderActionBar(
            state = orderState,
            onAddItemClicked = onAddItemClicked,
            onRemoveItemClicked = onRemoveItemClicked,
            onCheckOutItemClicked = onCheckOutItemClicked,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(
                    horizontal = 18.dp,
                    vertical = 8.dp
                )
        )

    }
}*/



class ProductDetailsViewModel : ViewModel() {
    private val firestoreRepo = FirestoreRepo()


    fun getCropDetails(cropId: String): Crop {
        val crops= listOf(
                Crop(
                    cropId = "beans",
                    name = "Beans",
                    type="",
                    quantity = 0,
                    cropImage = R.drawable.beans, // Replace with your actual drawable resource ID
                    cropDescription = "Beans are a good source of protein and essential nutrients.",
                    farmerId = "",
                    farmerName = "",
                    location = ""
            ),
            Crop(
                cropId = "rice",
                name = "rice",
                type="",
                quantity = 0,
                cropImage = R.drawable.rice, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "bananas",
                name = "Bananas",
                type="",
                quantity = 0,
                cropImage = R.drawable.bananas, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "oranges",
                name = "Oranges",
                type="",
                quantity = 0,
                cropImage = R.drawable.oranges, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "grapes",
                name = "Grapes",
                type="",
                quantity =0 ,
                cropImage = R.drawable.grapes, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "maize",
                name = "Maize",
                type="",
                quantity = 0,
                cropImage = R.drawable.maize, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "potatoes",
                name = "Potaotoes",
                type="",
                quantity = 0,
                cropImage = R.drawable.potatoes, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "cabbage",
                name = "Cabbage",
                type="",
                quantity =0 ,
                cropImage = R.drawable.cabbage, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "sukuma wiki",
                name = "Sukuma Wiki ",
                type="",
                quantity = 0,
                cropImage = R.drawable.sukuma_wiki, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "arrow roots",
                name = "Arrow Roots",
                type="",
                quantity =0 ,
                cropImage = R.drawable.arrow_roots, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "wheat",
                name = "Wheat",
                type="",
                quantity = 0,
                cropImage = R.drawable.wheat, // Replace with your actual drawable resource ID
                cropDescription = "Beans are a good source of protein and essential nutrients.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),

        )
        return crops.firstOrNull { it.cropId == cropId }
            ?: throw IllegalArgumentException("Crop not found with id: $cropId")

        //return getCropDetails(cropId)

        // Return details like name, image resource, etc. from a local source or hardcoded list.
    }

    fun getFarmersForCrop(cropId: String, onComplete: (List<Farmer>) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        val farmers = mutableListOf<Farmer>()

        db.collection("farmers")
            .whereEqualTo("cropId", cropId)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val farmer = document.toObject(Farmer::class.java)
                    farmers.add(farmer)
                }
                onComplete(farmers) // Pass the fetched list of farmers to the callback
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching farmers", exception)
                onComplete(emptyList()) // If failed, return an empty list
            }
    }

    fun placeOrder(cropId: String, farmerId: String) {
        val db= FirebaseFirestore.getInstance()
        val farmerRef = db.collection("farmers").document(farmerId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(farmerRef)
            val currentQuantity = snapshot.getLong("quantity") ?: 0

            if (currentQuantity > 0) {
                transaction.update(farmerRef, "quantity", currentQuantity - 1)
            } else {
                throw Exception("Farmer out of stock")
            }
        }.addOnSuccessListener {
            Log.d("Order", "Order placed successfully")
        }.addOnFailureListener { e ->
            Log.e("Order", "Order failed: ${e.message}")
        }
    }


}

data class Farmer(
    val id: String = "",
    val name: String = "",
    val cropId: String = "",
    val quantity: Int = 0
)


class FirestoreRepo {
    private val db = FirebaseFirestore.getInstance()

    fun fetchFarmersForCrop(cropId: String): List<Farmer> {
        val farmers = mutableListOf<Farmer>()

        // Assuming there's a collection 'farmers' in Firestore, and each document represents a farmer
        db.collection("farmers")
            .whereEqualTo("cropId", cropId) // Filter farmers by cropId
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val farmer = document.toObject(Farmer::class.java)
                    farmers.add(farmer)
                }
            }
            .addOnFailureListener { exception ->
                Log.w("FirestoreRepo", "Error getting documents: ", exception)
            }

        return farmers // This may need to be adjusted for asynchronous behavior
    }
}
/*
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    cropId: String,
    viewModel: ProductDetailsViewModel
) {
    // Fetch crop details
    val cropDetails = viewModel.getCropDetails(cropId)

    // State for storing the farmers
    val farmersState = remember { mutableStateOf<List<Farmer>>(emptyList()) }

    // Fetch farmer details asynchronously
    LaunchedEffect(cropId) {
        viewModel.getFarmersForCrop(cropId) { farmers ->
            farmersState.value = farmers
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // Crop Details
        Image(
            painter = painterResource(id = cropDetails.cropImage),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Text(text = cropDetails.name, style = MaterialTheme.typography.headlineSmall)
        Text(
            text = cropDetails.cropDescription,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Farmers Section
        Text(text = "Farmers Selling This Crop:", style = MaterialTheme.typography.headlineSmall)
        if (farmersState.value.isEmpty()) {
            Text(text = "Loading farmers...", style = MaterialTheme.typography.bodySmall)
        } else {
            farmersState.value.forEach { farmer ->
                FarmerCard(farmer = farmer, cropId = cropId, viewModel = viewModel)
            }
        }
    }
}*/



@Composable
fun FarmerCard(
    farmer: Farmer,
    cropId: String,
    viewModel: ProductDetailsViewModel
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Farmer: ${farmer.name}")
            Text(text = "Quantity: ${farmer.quantity} kg")

            // Button to order crop
            TextButton(onClick = {
                viewModel.placeOrder(cropId, farmer.id)
            }) {
                Text(text = "Order from ${farmer.name}")
            }
        }
    }
}













/*
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    cropId: String,
    viewModel: ProductDetailsViewModel
) {
    val cropDetails = viewModel.getCropDetails(cropId) // Fetch crop details from ViewModel
    val farmerDetails = viewModel.getFarmersForCrop(cropId) // Fetch farmer details from Firestore
   // Fetch nutrition data from Room

    Column {
        Image(painter = painterResource(id = cropDetails.cropImage), contentDescription = null)
        Text(text = cropDetails.name)
        Text(text = cropDetails.cropDescription)

        farmerDetails.forEach { farmer ->
            Text(text = "Farmer: ${farmer.name}, Quantity: ${farmer.quantity}")
        }




    }
}*/


/*
fun ProductDetailsScreen(
    productId: Int, // Pass product ID from catalog to product details screen
    productDetailsViewModel: ProductDetailsViewModel = viewModel()
) {
    val product by productDetailsViewModel.product.observeAsState()

    // Fetch product details on the first composition
    LaunchedEffect(productId) {
        productDetailsViewModel.getProductDetails(productId)
    }

    // Show loading state or the product details
    product?.let { product ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Content(
                productPreviewState = ProductPreviewState(imageUrl = product.imageUrl),
                productFlavours = listOf(ProductFlavourState(flavour = product.flavour)),
                productNutritionState = ProductNutritionState(nutrition = product.nutrition),
                productDescription = product.description
            )
            OrderActionBar(
                state = OrderData,
                onAddItemClicked = {},
                onRemoveItemClicked = {},
                onCheckOutItemClicked = {},
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 8.dp)
            )
        }
    } ?: run {
        // You can show a loading state here if the product is not yet loaded
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}*/
@Composable
fun ProductDetailsScreen(
    navController: NavController,
    cropId: String,
    viewModel: ProductDetailsViewModel
) {
    val cropDetails = viewModel.getCropDetails(cropId)
    val farmersState = remember { mutableStateOf<List<Farmer>>(emptyList()) }

    LaunchedEffect(cropId) {
        viewModel.getFarmersForCrop(cropId) { farmers ->
            farmersState.value = farmers
        }
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Image(
            painter = painterResource(id = cropDetails.cropImage),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(200.dp)
        )
        Text(text = cropDetails.name, style = MaterialTheme.typography.headlineSmall)
        Text(
            text = cropDetails.cropDescription,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Farmers Selling This Crop:", style = MaterialTheme.typography.headlineSmall)

        if (farmersState.value.isEmpty()) {
            Text(text = "Loading farmers...", style = MaterialTheme.typography.bodySmall)
        } else {
            farmersState.value.forEach { farmer ->
                FarmerCard(farmer = farmer, cropId = cropId, viewModel = viewModel)
            }
        }
    }
}



@Composable
private fun Content(
    modifier: Modifier = Modifier,
    productPreviewState: ProductPreviewState,
    productFlavours:  List<ProductFlavourState> ,
    productNutritionState: ProductNutritionState,
    productDescription: String
) {
    val scrollableState= rememberScrollState()

    Column (
        modifier=modifier.verticalScroll(scrollableState)
    ){
        ProductPreviewSection(
            state=productPreviewState
        )
        Spacer(
            modifier =Modifier.height(16.dp)
        )
        FlavourSection(
            modifier= Modifier.padding(18.dp),
            data = productFlavours

        )
        Spacer(
            modifier =Modifier.height(16.dp)
        )
        ProductNutritionSection(
            modifier.padding(horizontal = 18.dp),
            state =productNutritionState
        )
        Spacer(
            modifier = Modifier.height(32.dp)
        )
        ProductDescriptionSection(
            productDescription = productDescription,
            modifier = Modifier
                .navigationBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(bottom = 128.dp)
        )

    }

}

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen()
        }
        composable(
            "productDetails/{cropId}",
            arguments = listOf(navArgument("cropId") { type = NavType.StringType })
        ) { backStackEntry ->
            val cropId = backStackEntry.arguments?.getString("cropId") ?: ""
            val viewModel = ProductDetailsViewModel() // Or use hiltViewModel() if using DI
            ProductDetailsScreen(navController, cropId, viewModel)
        }
    }
}
