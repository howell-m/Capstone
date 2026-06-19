package com.example.showpilot.data.repository

import androidx.lifecycle.LiveData
import com.example.showpilot.data.dao.ExpenseDao
import com.example.showpilot.data.entities.Expense

class ExpenseRepository(private val dao: ExpenseDao) {

    suspend fun insert(expense: Expense) {
        dao.insert(expense)
    }

    suspend fun update(expense: Expense) {
        dao.update(expense)
    }

    suspend fun delete(expense: Expense) {
        dao.delete(expense)
    }

    fun getAllExpense(): LiveData<List<Expense>> {
        return dao.getAllExpense()
    }
}