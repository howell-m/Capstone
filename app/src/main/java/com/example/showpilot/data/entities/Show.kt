package com.example.showpilot.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class Show(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val city: String,
    val venue: String,
    val date: String,
    val time: String
)