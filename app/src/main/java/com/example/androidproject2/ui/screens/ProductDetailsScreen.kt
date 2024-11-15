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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

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

    fun getFarmersForCrop(cropId: String): List<Farmer> {
        // Fetch farmer details from Firestore based on the selected cropId.
        return firestoreRepo.fetchFarmersForCrop(cropId)
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
}


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