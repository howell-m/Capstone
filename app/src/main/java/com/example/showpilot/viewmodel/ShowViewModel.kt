package com.example.showpilot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.showpilot.data.entities.Show
import com.example.showpilot.data.repository.ShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowViewModel(private val repository: ShowRepository) : ViewModel() {

    val allShows: LiveData<List<Show>> = repository.allShows

    fun insert(show: Show) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(show)
    }

    fun update(show: Show) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(show)
    }

    fun delete(show: Show) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(show)
    }
}