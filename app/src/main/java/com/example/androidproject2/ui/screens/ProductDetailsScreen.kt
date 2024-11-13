package com.example.androidproject2.ui.screens

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

@Preview(showBackground = true)
@Composable
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