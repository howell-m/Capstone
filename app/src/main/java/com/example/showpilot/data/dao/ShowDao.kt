package com.example.showpilot.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.showpilot.data.entities.Show

@Dao
interface ShowDao {

    @Query("SELECT * FROM shows ORDER BY date ASC")
    fun getAllShows(): LiveData<List<Show>>

    @Query("SELECT * FROM shows WHERE id = :id")
    fun getShowById(id: Int): LiveData<Show>

    //insert
    @Insert
    suspend fun insert(show: Show)

    //delete
    @Delete
    suspend fun delete(show: Show)

    //update
    @Update
    suspend fun update(show: Show)

    @Query("DELETE FROM shows")
    suspend fun deleteAll()
}