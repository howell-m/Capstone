package com.example.showpilot.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lodging")
data class Lodging(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val showId: Int,
    val hotelName: String,
    val address: String,
    val checkIn: String,
    val checkOut: String
)