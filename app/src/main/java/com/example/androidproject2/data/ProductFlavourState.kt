package com.example.androidproject2.data

import androidx.annotation.DrawableRes
import com.example.androidproject2.R

data class ProductFlavourState(
    val name:String,
    val price:String,
    @DrawableRes val imgRes:Int
)

val ProductFlavourData= listOf(
    ProductFlavourState(
        imgRes= R.drawable.maize,
        name="Maize grain",
        price="Ksh.300 per kg"
    ),
    ProductFlavourState(
        imgRes= R.drawable.beans,
        name="Beans",
        price="Ksh.200 per kg"
    ),
    ProductFlavourState(
        imgRes= R.drawable.bananas,
        name="Bananas",
        price="Ksh.150 per bunch"
    ),
    ProductFlavourState(
        imgRes= R.drawable.cabbage,
        name="Cabbage",
        price="Ksh.120 per cabbage"
    ),
    ProductFlavourState(
        imgRes= R.drawable.arrow_roots,
        name="Arrow roots",
        price="Ksh.300 per kg"
    ),
    ProductFlavourState(
        imgRes= R.drawable.grapes,
        name="Grapes",
        price="Ksh.250 per bunch"
    ),
    ProductFlavourState(
        imgRes= R.drawable.potatoes,
        name="Potatoes",
        price="Ksh.300 per kg"
    )

)