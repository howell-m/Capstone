package com.example.showpilot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.showpilot.R
import com.example.showpilot.data.entities.Expense

class ExpenseAdapter(
    private var expense: MutableList<Expense>,
    private val onDeleteClick: (Expense) -> Unit
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvExpenseTitle: TextView = itemView.findViewById(R.id.tvExpenseTitle)
        val tvExpenseAmount: TextView = itemView.findViewById(R.id.tvExpenseAmount)
        val tvExpenseDate: TextView = itemView.findViewById(R.id.tvExpenseDate)
        val tvExpenseLocation: TextView = itemView.findViewById(R.id.tvExpenseLocation)

        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDeleteExpense)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expense[position]

        holder.tvExpenseTitle.text = expense.name
        holder.tvExpenseAmount.text = "$%.2f".format(expense.amount)
        holder.tvExpenseDate.text = expense.date
        holder.tvExpenseLocation.text = expense.location
        holder.deleteButton.setOnClickListener {
            onDeleteClick(expense)
        }
    }

    override fun getItemCount(): Int = expense.size

    fun updateExpenses(newExpenses: List<Expense>) {
        expense.clear()
        expense.addAll(newExpenses)
        notifyDataSetChanged()
    }
}