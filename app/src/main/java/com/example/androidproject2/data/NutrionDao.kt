package com.example.androidproject2.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import com.example.androidproject2.ui.screens.AppDatabase


//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Entity(tableName = "nutrition_data")
data class NutritionData(
    @PrimaryKey val cropId: String, // Crop identifier
    val calories: String,
    val protein: String,
    val fat: String,
    val carbohydrates: String,
    val fiber: String
)


@Dao
interface NutritionDao {

    // Query to get nutrition data for a specific crop by cropId
    @Query("SELECT * FROM nutrition_data WHERE cropId = :cropId LIMIT 1")
    suspend fun getNutritionDataForCrop(cropId: String): NutritionData?
}


