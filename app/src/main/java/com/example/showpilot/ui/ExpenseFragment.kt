package com.example.showpilot.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showpilot.R
import com.example.showpilot.viewmodel.ExpenseViewModel
import androidx.navigation.fragment.findNavController

class ExpenseFragment : Fragment(R.layout.fragment_expense) {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var adapter: ExpenseAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var totalExpenseAmount: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewExpense)
        totalExpenseAmount = view.findViewById(R.id.tvTotalExpenseAmount)

        val addExpenseButton = view.findViewById<ImageButton>(R.id.btnAddExpense)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        adapter = ExpenseAdapter(mutableListOf()) { expense ->
            expenseViewModel.delete(expense)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        addExpenseButton.setOnClickListener {
            findNavController().navigate(R.id.action_expenseFragment_to_addEditExpense)
        }


        expenseViewModel.allExpense.observe(viewLifecycleOwner) { expense ->
            adapter.updateExpenses(expense)

            val total = expense.sumOf { it.amount }
            totalExpenseAmount.text = "$%.2f".format(total)
        }

    }
}