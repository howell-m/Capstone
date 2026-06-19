package com.example.showpilot.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.showpilot.data.entities.Lodging

@Dao
interface LodgingDao {

    @Insert
    suspend fun insert(lodging: Lodging)

    @Update
    suspend fun update(lodging: Lodging)

    @Query("SELECT * FROM lodging WHERE showId = :showId LIMIT 1")
    fun getLodgingForShow(showId: Int): LiveData<Lodging?>

    @Query("SELECT * FROM lodging")
    fun getAllLodgings(): LiveData<List<Lodging>>
}