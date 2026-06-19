package com.example.showpilot.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.showpilot.R
import com.example.showpilot.data.entities.Expense
import com.example.showpilot.viewmodel.ExpenseViewModel
import java.util.Calendar

class AddEditExpense : Fragment(R.layout.fragment_add_edit_expense) {

    private lateinit var expenseViewModel: ExpenseViewModel

    private var expenseId: Int = 0
    private var showId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        val expenseNameInput = view.findViewById<EditText>(R.id.titleInput)
        val amountInput = view.findViewById<EditText>(R.id.amountInput)
        val dateInput = view.findViewById<EditText>(R.id.dateInput)
        val locationInput = view.findViewById<EditText>(R.id.locationInput)
        val saveExpenseButton = view.findViewById<Button>(R.id.updateExpenseButton)

        expenseId = arguments?.getInt("expense_id", 0) ?: 0
        showId = arguments?.getInt("show_id", 0) ?: 0

        val expenseName = arguments?.getString("expense_name") ?: ""
        val amount = arguments?.getString("amount") ?: ""
        val date = arguments?.getString("date") ?: ""
        val location = arguments?.getString("location") ?: ""

        expenseNameInput.setText(expenseName)
        amountInput.setText(amount)
        dateInput.setText(date)
        locationInput.setText(location)

        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedMonth + 1,
                        selectedDay,
                        selectedYear
                    )
                    dateInput.setText(formattedDate)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        saveExpenseButton.setOnClickListener {
            val updatedExpenseName = expenseNameInput.text.toString().trim()
            val updatedAmountText = amountInput.text.toString().trim()
            val updatedDate = dateInput.text.toString().trim()
            val updatedLocation = locationInput.text.toString().trim()

            if (updatedExpenseName.isEmpty() ||
                updatedAmountText.isEmpty() ||
                updatedDate.isEmpty() ||
                updatedLocation.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val updatedAmount = updatedAmountText.toDoubleOrNull()
            if (updatedAmount == null) {
                Toast.makeText(requireContext(), "Enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val expense = Expense(
                id = expenseId,
                showId = showId,
                name = updatedExpenseName,
                amount = updatedAmount,
                date = updatedDate,
                location = updatedLocation
            )

            if (expenseId == 0) {
                expenseViewModel.insert(expense)
            } else {
                expenseViewModel.update(expense)
            }

            findNavController().popBackStack()
        }
    }
}