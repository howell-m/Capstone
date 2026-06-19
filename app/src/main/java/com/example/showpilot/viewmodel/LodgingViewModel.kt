package com.example.showpilot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.showpilot.data.AppDatabase
import com.example.showpilot.data.entities.Lodging
import com.example.showpilot.data.repository.LodgingRepository
import kotlinx.coroutines.launch

class LodgingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LodgingRepository
    val allLodgings: LiveData<List<Lodging>>

    init {
        val dao = AppDatabase.getDatabase(application).lodgingDao()
        repository = LodgingRepository(dao)
        allLodgings = repository.getAllLodgings()
    }

    fun insert(lodging: Lodging) = viewModelScope.launch {
        repository.insert(lodging)
    }

    fun update(lodging: Lodging) = viewModelScope.launch {
        repository.update(lodging)
    }

    fun getLodgingForShow(showId: Int): LiveData<Lodging?> {
        return repository.getLodgingForShow(showId)
    }
}