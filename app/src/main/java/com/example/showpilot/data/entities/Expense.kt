package com.example.showpilot.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense")
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val showId: Int,
    val name: String,
    val amount: Double,
    val date: String,
    val location: String
)