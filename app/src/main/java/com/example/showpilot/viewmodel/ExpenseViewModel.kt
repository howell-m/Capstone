package com.example.showpilot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.showpilot.data.AppDatabase
import com.example.showpilot.data.entities.Expense
import com.example.showpilot.data.repository.ExpenseRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository
    val allExpense: LiveData<List<Expense>>

    init {
        val dao = AppDatabase.getDatabase(context = application).expenseDao()
        repository = ExpenseRepository(dao)
        allExpense = repository.getAllExpense()
    }

    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }

    fun update(expense: Expense) = viewModelScope.launch {
        repository.update(expense)
    }

    fun delete(expense: Expense) = viewModelScope.launch {
        repository.delete(expense)
    }
}

