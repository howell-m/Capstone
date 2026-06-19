package com.example.showpilot.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.showpilot.R
import com.example.showpilot.data.AppDatabase
import com.example.showpilot.data.entities.Show
import com.example.showpilot.data.repository.ShowRepository
import com.example.showpilot.viewmodel.ShowViewModel
import com.example.showpilot.viewmodel.ShowViewModelFactory
import java.util.Calendar

class AddShowFragment : Fragment(R.layout.fragment_add_show) {

    private lateinit var viewModel: ShowViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etVenue = view.findViewById<EditText>(R.id.etVenue)
        val etCity = view.findViewById<EditText>(R.id.etCity)
        val etTime = view.findViewById<EditText>(R.id.etTime)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val btnAdd = view.findViewById<Button>(R.id.btnAddShow)

        val calendar = Calendar.getInstance()

        etDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    val formattedDate = String.format(
                        "%02d/%02d/%04d",
                        selectedMonth + 1,
                        selectedDay,
                        selectedYear
                    )

                    etDate.setText(formattedDate)
                },
                year,
                month,
                day
            )

            datePicker.show()
        }

        etTime.setOnClickListener {
            showScrollableTimePicker(etTime)
        }

        val dao = AppDatabase.getDatabase(requireContext()).showDao()
        val repository = ShowRepository(dao)
        val factory = ShowViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShowViewModel::class.java]

        val showId = arguments?.getInt("SHOW_ID", 0) ?: 0
        val venue = arguments?.getString("SHOW_VENUE", "") ?: ""
        val city = arguments?.getString("SHOW_CITY", "") ?: ""
        val date = arguments?.getString("SHOW_DATE", "") ?: ""
        val time = arguments?.getString("SHOW_TIME", "") ?: ""

        if (showId != 0) {
            etVenue.setText(venue)
            etCity.setText(city)
            etDate.setText(date)
            etTime.setText(time)
            btnAdd.text = "Update Show"
        }

        btnAdd.setOnClickListener {
            val updatedVenue = etVenue.text.toString().trim()
            val updatedCity = etCity.text.toString().trim()
            val updatedDate = etDate.text.toString().trim()
            val updatedTime = etTime.text.toString().trim()

            if (updatedVenue.isNotEmpty() && updatedCity.isNotEmpty()) {
                val show = Show(
                    id = showId,
                    venue = updatedVenue,
                    city = updatedCity,
                    date = updatedDate,
                    time = updatedTime
                )

                if (showId == 0) {
                    viewModel.insert(show)
                } else {
                    viewModel.update(show)
                }

                findNavController().popBackStack()
            }
        }
    }

    private fun showScrollableTimePicker(timeInput: EditText) {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.HORIZONTAL
            setPadding(40, 30, 40, 10)
        }

        val hourPicker = NumberPicker(requireContext()).apply {
            minValue = 1
            maxValue = 12
            value = 9
        }

        val minutePicker = NumberPicker(requireContext()).apply {
            minValue = 0
            maxValue = 59
            value = 0
            setFormatter { value -> String.format("%02d", value) }
        }

        val amPmPicker = NumberPicker(requireContext()).apply {
            minValue = 0
            maxValue = 1
            displayedValues = arrayOf("AM", "PM")
            value = 1
        }

        layout.addView(hourPicker)
        layout.addView(minutePicker)
        layout.addView(amPmPicker)

        AlertDialog.Builder(requireContext())
            .setTitle("Select Time")
            .setView(layout)
            .setPositiveButton("OK") { _, _ ->
                val hour = hourPicker.value
                val minute = String.format("%02d", minutePicker.value)
                val amPm = if (amPmPicker.value == 0) "AM" else "PM"

                timeInput.setText("$hour:$minute $amPm")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}