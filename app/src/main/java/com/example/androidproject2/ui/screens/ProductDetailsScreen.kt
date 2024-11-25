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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ProductDetailsViewModel : ViewModel() {
    private val firestoreRepo = FirestoreRepo()


    fun getCropDetails(cropId: String): Crop {
        val crops = listOf(
            Crop(
                cropId = "beans",
                name = "Beans",
                type = "",
                quantity = 0,
                cropImage = R.drawable.beans, // Replace with your actual drawable resource ID
                cropDescription = "Beans are highly nutritious legumes that are a staple food in many cultures around the world. Available in a wide variety of types, such as black beans, kidney beans, lentils, and chickpeas, they are rich in protein, fiber, vitamins, and minerals like iron and folate. Beans are an excellent plant-based protein source, making them ideal for vegetarians and vegans. They are versatile in cooking, used in soups, stews, salads, and as side dishes. Beyond their nutritional benefits, beans promote heart health, aid digestion, and help maintain steady blood sugar levels, making them a wholesome and sustainable food choice.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "rice",
                name = "rice",
                type = "",
                quantity = 0,
                cropImage = R.drawable.rice, // Replace with your actual drawable resource ID
                cropDescription = "Rice is a staple food for more than half the world’s population, known for its versatility and adaptability in various cuisines. It comes in many varieties, including white, brown, and aromatic types like basmati or jasmine. Rich in carbohydrates, rice provides energy and is often fortified with essential nutrients like iron and B vitamins. It pairs well with vegetables, meats, and spices, making it a cornerstone in dishes ranging from stir-fries to risottos and curries.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "bananas",
                name = "Bananas",
                type = "",
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
                type = "",
                quantity = 0,
                cropImage = R.drawable.oranges, // Replace with your actual drawable resource ID
                cropDescription = "Oranges are juicy, citrus fruits prized for their refreshing taste and rich nutritional profile. Packed with vitamin C, they boost the immune system and provide antioxidants that promote skin health and fight free radicals. Oranges are versatile, enjoyed fresh, as juice, or as an ingredient in salads, desserts, and marinades. Their tangy sweetness makes them a popular snack, while their fiber content aids digestion and supports overall health.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "grapes",
                name = "Grapes",
                type = "",
                quantity = 0,
                cropImage = R.drawable.grapes, // Replace with your actual drawable resource ID
                cropDescription = "Grapes are small, sweet, and juicy fruits that grow in clusters and are enjoyed fresh, dried as raisins, or processed into products like wine, juice, and jelly. They are rich in antioxidants, particularly resveratrol, which supports heart health and combats inflammation. Grapes come in various colors, including red, green, and purple, and their natural sweetness makes them a healthy, portable snack that’s also used in desserts and salads.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "maize",
                name = "Maize",
                type = "",
                quantity = 0,
                cropImage = R.drawable.maize, // Replace with your actual drawable resource ID
                cropDescription = "Maize, also known as corn, is a versatile and globally important cereal crop. It serves as a dietary staple in many regions and is consumed in various forms, including fresh, dried, or processed into flour, snacks, or syrups. Maize is rich in carbohydrates, providing a significant source of energy, and also contains essential vitamins like B-complex and minerals like magnesium. Beyond human consumption, maize is a key ingredient in animal feed and industrial products like biofuel, underscoring its economic significance.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "potatoes",
                name = "Potaotoes",
                type = "",
                quantity = 0,
                cropImage = R.drawable.potatoes, // Replace with your actual drawable resource ID
                cropDescription = "Potatoes are nutrient-dense tubers and one of the world's most widely consumed vegetables. They are rich in carbohydrates, fiber, and vitamins like C and B6, as well as potassium, which supports heart health. Versatile in the kitchen, potatoes can be boiled, roasted, mashed, or fried, and are the base for countless dishes across different cuisines. Their mild flavor and adaptability make them a culinary favorite globally.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "cabbage",
                name = "Cabbage",
                type = "",
                quantity = 0,
                cropImage = R.drawable.cabbage, // Replace with your actual drawable resource ID
                cropDescription = "Cabbage is a leafy vegetable belonging to the cruciferous family, known for its dense nutrient content and culinary versatility. It is rich in vitamins C and K, dietary fiber, and antioxidants, which promote digestion, boost immunity, and support heart health. Cabbage can be eaten raw in salads, fermented as sauerkraut, or cooked in soups and stir-fries, making it a staple in many global cuisines.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "sukuma wiki",
                name = "Sukuma Wiki ",
                type = "",
                quantity = 0,
                cropImage = R.drawable.sukuma_wiki, // Replace with your actual drawable resource ID
                cropDescription = "Sukuma wiki, also known as collard greens, is a popular leafy vegetable in East Africa, particularly in Kenya. It is rich in vitamins A, C, and K, calcium, and dietary fiber, making it a nutritious addition to meals. Typically sautéed with onions, tomatoes, and spices, sukuma wiki is an affordable and versatile dish often served alongside staples like ugali or rice, promoting health and sustenance.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "arrow roots",
                name = "Arrow Roots",
                type = "",
                quantity = 0,
                cropImage = R.drawable.arrow_roots, // Replace with your actual drawable resource ID
                cropDescription = "Arrowroots are starchy tubers that are highly valued for their easy digestibility and nutritional benefits. They are a rich source of carbohydrates and provide essential minerals like calcium and potassium. Often boiled or steamed, arrowroots are consumed as a healthy alternative to potatoes or other root crops. Their mild flavor makes them a versatile ingredient in soups, baked goods, and traditional dishes.",
                farmerId = "",
                farmerName = "",
                location = ""
            ),
            Crop(
                cropId = "wheat",
                name = "Wheat",
                type = "",
                quantity = 0,
                cropImage = R.drawable.wheat, // Replace with your actual drawable resource ID
                cropDescription = "Wheat is a staple cereal crop that forms the foundation of many diets around the world. It is the primary ingredient in bread, pasta, pastries, and numerous other baked goods. Rich in carbohydrates, wheat also provides dietary fiber, B vitamins, and essential minerals like iron and zinc. Whole wheat products are particularly valued for their role in promoting heart health and aiding digestion.\n" +
                        "\n",
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

        db.collection("crops") // Assuming the collection is "crops"
            .whereEqualTo("name", cropId) // Filter farmers based on cropId
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val farmer = Farmer(
                        id = document.id,
                        FarmerName = document.getString("farmerName") ?: "Unknown",
                        cropId = document.getString("name") ?: "",
                        quantity = document.getLong("quantity")?.toInt() ?: 0
                    )
                    farmers.add(farmer)
                }
                onComplete(farmers) // Return the fetched list of farmers
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching farmers: ${exception.message}")
                onComplete(emptyList()) // If the fetch fails, return an empty list
            }
    }


    /*  fun placeOrder(
        cropId: String,
        farmerId: String,
        orderQuantity: Int,
        farmerName: String,
        customerId: String,
        customerName: String,
        location: String,
        deliveryAddress: String
        ) {
        val db = FirebaseFirestore.getInstance()
        val farmerRef = db.collection("crops").document(farmerId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(farmerRef)
            val currentQuantity = snapshot.getLong("quantity")?.toInt() ?: 0

            if (currentQuantity >= orderQuantity) {
                transaction.update(farmerRef, "quantity", currentQuantity - orderQuantity)
                // Add sale record
                val sale = hashMapOf(
                    "farmerId" to farmerId,
                    "farmerName" to farmerName,
                    "customerId" to customerId,
                    "customerName" to customerName,
                    "cropId" to cropId,
                    "quantity" to orderQuantity,
                    "location" to location,
                    "deliveryAddress" to deliveryAddress,
                    "timestamp" to System.currentTimeMillis() // Add a timestamp for sorting
                )
                db.collection("sales").add(sale)
            } else {
                throw Exception("Order quantity exceeds available stock.")
            }
        }.addOnSuccessListener {
            Log.d("Order", "Order placed successfully for $orderQuantity kg")
        }.addOnFailureListener { e ->
            Log.e("Order", "Order failed: ${e.message}")
        }

}*/

    fun placeOrder(
        cropId: String,
        farmerId: String,
        orderQuantity: Int,
        farmerName: String,
        customerId: String,
        customerName: String,
        location: String, // This should now contain the correct location
        deliveryAddress: String
    ) {
        val db = FirebaseFirestore.getInstance()
        val farmerRef = db.collection("crops").document(farmerId)

        db.runTransaction { transaction ->
            val snapshot = transaction.get(farmerRef)
            val currentQuantity = snapshot.getLong("quantity")?.toInt() ?: 0

            if (currentQuantity >= orderQuantity) {
                transaction.update(farmerRef, "quantity", currentQuantity - orderQuantity)
                // Add sale record
                val sale = hashMapOf(
                    "farmerId" to farmerId,
                    "farmerName" to farmerName,
                    "customerId" to customerId,
                    "customerName" to customerName,
                    "cropId" to cropId,
                    "quantity" to orderQuantity,
                    "location" to location, // Use the passed location
                    "deliveryAddress" to deliveryAddress,
                    "timestamp" to System.currentTimeMillis()
                )
                db.collection("sales").add(sale)
            } else {
                throw Exception("Order quantity exceeds available stock.")
            }
        }.addOnSuccessListener {
            Log.d("Order", "Order placed successfully for $orderQuantity kg")
        }.addOnFailureListener { e ->
            Log.e("Order", "Order failed: ${e.message}")
        }
    }
}

data class Farmer(
    val id: String = "",
    val FarmerName: String = "",
    val name: String = "",
    val cropId: String = "",
    val quantity: Int = 0,
    val location: String=""
) {

}


class FirestoreRepo {
    private val db = FirebaseFirestore.getInstance()

    fun fetchFarmersForCrop(cropId: String): List<Farmer> {
        val farmers = mutableListOf<Farmer>()

        // Assuming there's a collection 'farmers' in Firestore, and each document represents a farmer
        db.collection("crops")
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
fun FarmerCard(
    farmer: Farmer,
    cropId: String,
    navController: NavController
) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Farmer: ${farmer.FarmerName}")
            Text(text = "Quantity: ${farmer.quantity} kg")

            // Navigate to OrderScreen
            TextButton(onClick = {
                navController.navigate("order_screen/${farmer.id}/${cropId}")
            }) {
                Text(text = "Order from ${farmer.FarmerName}")
            }
        }
    }
}


@Composable
fun ProductDetailsScreen(
    navController: NavController,
    cropId: String,
    viewModel: ProductDetailsViewModel
) {
    if (cropId.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Invalid Crop ID", color = MaterialTheme.colorScheme.error)
        }
        return
    }

    // Fetch crop details
    val cropDetails = viewModel.getCropDetails(cropId) ?: Crop(
        cropId = "unknown",
        name = "Unknown Crop",
        type = "",
        quantity = 0,
        location = "",
        farmerId = "",
        farmerName = "",
        cropImage = R.drawable.beans,
        cropDescription = "No description available"
    )

    // State to store fetched farmers
    val farmersState = remember { mutableStateOf<List<Farmer>>(emptyList()) }

    // Fetch farmers when the screen is launched
    LaunchedEffect(cropId) {
        viewModel.getFarmersForCrop(cropId) { farmers ->
            farmersState.value = farmers
        }
    }

    // UI Layout
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
                FarmerCard(farmer = farmer, cropId = cropId, navController)
            }
        }
    }
}
@Composable
fun OrderScreen(
    navController: NavController,
    farmerId: String,
    cropId: String,
    viewModel: ProductDetailsViewModel
) {
    val db = FirebaseFirestore.getInstance()
    var farmer by remember { mutableStateOf<Farmer?>(null) }
    var customerName by remember { mutableStateOf("") }
    var customerId by remember { mutableStateOf("") }
    var cropLocation by remember { mutableStateOf("") }

    var quantity by remember { mutableStateOf("") }
    var deliveryAddress by remember { mutableStateOf("") }
    val isQuantityValid = remember(quantity) {
        quantity.toIntOrNull()?.let { it > 0 && farmer?.quantity?.let { available -> it <= available } == true } ?: false
    }

    // Fetch Farmer Details
    // Fetch Farmer Details
    LaunchedEffect(farmerId) {
        db.collection("crops").document(farmerId).get()
            .addOnSuccessListener { document ->
                farmer = Farmer(
                    id = document.id,
                    FarmerName = document.getString("farmerName") ?: "Unknown",
                    name = document.getString("name") ?: "",
                    quantity = document.getLong("quantity")?.toInt() ?: 0,
                    location = document.getString("location") ?: ""
                )
            }
            .addOnFailureListener { exception ->
                Log.e("OrderScreen", "Error fetching farmer details: ${exception.message}")
            }
    }

    // Fetch Customer Details
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            customerId = currentUser.uid
            customerName = currentUser.displayName ?: "Unknown"
        }
    }

    // Fetch Crop Location
    LaunchedEffect(cropId) {
        db.collection("crops").document(cropId).get()
            .addOnSuccessListener { document ->
                cropLocation = document.getString("location") ?: "Unknown Location"
            }
            .addOnFailureListener { exception ->
                Log.e("OrderScreen", "Error fetching crop location: ${exception.message}")
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (farmer == null) {
            Text(text = "Loading farmer details...", style = MaterialTheme.typography.bodyLarge)
        } else {
            // Non-Editable Farmer Details
            Text(text = "Farmer: ${farmer?.FarmerName}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Crop: ${farmer?.name}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Location: ${farmer?.location}", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))


            // Editable Quantity Field
            TextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity (Available: ${farmer?.quantity} kg)") },
                isError = !isQuantityValid,
                modifier = Modifier.fillMaxWidth()
            )
            if (!isQuantityValid && quantity.isNotEmpty()) {
                Text(
                    text = "Invalid quantity. Must be between 1 and ${farmer?.quantity}.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Editable Delivery Address
            TextField(
                value = deliveryAddress,
                onValueChange = { deliveryAddress = it },
                label = { Text("Delivery Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Submit Order Button
            TextButton(
                onClick = {

                    if (isQuantityValid && deliveryAddress.isNotBlank()) {
                        val orderQuantity = quantity.toInt()
                        viewModel.placeOrder(
                            cropId = cropId,
                            farmerId = farmerId,
                            orderQuantity = orderQuantity,
                            farmerName = farmer?.FarmerName ?: "Unknown",
                            customerId = customerId,
                            customerName = customerName,
                            location = cropLocation,
                            deliveryAddress = deliveryAddress
                        )
                        navController.popBackStack() // Navigate back after placing the order
                    }
                },
                enabled = isQuantityValid && deliveryAddress.isNotBlank() && cropLocation.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Place Order")
            }
        }
    }
}







