package com.example.showpilot.data.repository

import androidx.lifecycle.LiveData
import com.example.showpilot.data.dao.LodgingDao
import com.example.showpilot.data.entities.Lodging

class LodgingRepository(private val dao: LodgingDao) {

    suspend fun insert(lodging: Lodging) {
        dao.insert(lodging)
    }

    suspend fun update(lodging: Lodging) {
        dao.update(lodging)
    }

    fun getLodgingForShow(showId: Int): LiveData<Lodging?> {
        return dao.getLodgingForShow(showId)
    }

    fun getAllLodgings(): LiveData<List<Lodging>> {
        return dao.getAllLodgings()
    }
}