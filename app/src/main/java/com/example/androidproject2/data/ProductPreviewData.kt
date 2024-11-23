package com.example.androidproject2.data

import androidx.annotation.DrawableRes
import com.example.androidproject2.R

data class ProductHighlightState(
    val text:String,
    val type:ProductHighlightType
)

enum class ProductHighlightType{
    PRIMARY, SECONDARY
}

data class ProductPreviewState(
    val headline: String = "GreenShop",
    @DrawableRes val productImg: Int= R.drawable.bananas,
    val highlights: List<ProductHighlightState> = listOf(
        ProductHighlightState(
            text = "Fresh vegetables",
            type= ProductHighlightType.SECONDARY
        ),
        ProductHighlightState(
            text = "Nearest to you",
            type=ProductHighlightType.PRIMARY
        )

    )

) {

}