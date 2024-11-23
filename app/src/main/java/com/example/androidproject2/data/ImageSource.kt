package com.example.androidproject2.data

import com.example.androidproject2.R
import com.example.androidproject2.model.Catalog

class ImageSource {
    fun loadStock(): List<Catalog>{
        return listOf<Catalog>(
            Catalog(R.string.crop1, R.drawable.beans, "250 Per kg", 250, "link"),
            Catalog(R.string.crop2, R.drawable.rice, "200 per kg", 200, "link"),
            Catalog(R.string.crop3, R.drawable.bananas, "150 per bunch", 150, "link"),
            Catalog(R.string.crop4, R.drawable.oranges, "300 per kg", 300,"link0"),
            Catalog(R.string.crop5, R.drawable.grapes, "1000 per kg", 1000,"link0"),
            Catalog(R.string.crop6, R.drawable.maize,"300 per kg", 300,"link0"),
            Catalog(R.string.crop7, R.drawable.potatoes,"300 per kg", 300,"link0"),
            Catalog(R.string.crop8, R.drawable.cabbage,"120 per kg", 120,"link0"),
            Catalog(R.string.crop9, R.drawable.sukuma_wiki,"300 per kg", 300,"link0"),
            Catalog(R.string.crop10, R.drawable.arrow_roots,"500 per kg", 500,"link0"),
            Catalog(R.string.crop11, R.drawable.wheat,"400 per kg", 400,"link0"),
        )

    }
}