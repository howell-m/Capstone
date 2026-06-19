package com.example.showpilot.data.repository

import androidx.lifecycle.LiveData
import com.example.showpilot.data.dao.ShowDao
import com.example.showpilot.data.entities.Show

class ShowRepository(private val showDao: ShowDao) {

    val allShows: LiveData<List<Show>> = showDao.getAllShows()

    suspend fun insert(show: Show) {
        showDao.insert(show)
    }

    suspend fun update(show: Show) {
        showDao.update(show)
    }

    suspend fun delete(show: Show) {
        showDao.delete(show)
    }
}