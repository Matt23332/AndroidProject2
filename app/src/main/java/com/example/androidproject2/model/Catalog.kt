package com.example.androidproject2.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Catalog (
    val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int,
    val price: String,
    val price_per_kg: Int,
    val ProductLink: String,

    ) {

}

