package com.example.showpilot.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.showpilot.R
import com.example.showpilot.data.entities.Lodging
import com.example.showpilot.viewmodel.LodgingViewModel
import android.app.DatePickerDialog
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

class AddEditLodging : Fragment(R.layout.fragment_add_edit_lodging) {

    private lateinit var lodgingViewModel: LodgingViewModel

    private var lodgingId: Int = 0
    private var showId: Int = 0

    private var checkInDateMillis: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lodgingViewModel = ViewModelProvider(this)[LodgingViewModel::class.java]

        val hotelNameInput = view.findViewById<EditText>(R.id.hotelNameInput)
        val addressInput = view.findViewById<EditText>(R.id.addressInput)

// CHECK-IN DATE PICKER
        val checkInInput = view.findViewById<EditText>(R.id.checkInInput)

        checkInInput.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, day)

                    checkInDateMillis = selectedCalendar.timeInMillis

                    checkInInput.setText("${month + 1}/$day/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

// CHECK-OUT DATE PICKER
        val checkOutInput = view.findViewById<EditText>(R.id.checkOutInput)

        checkOutInput.setOnClickListener {

            if (checkInDateMillis == null) {
                Toast.makeText(requireContext(), "Select check-in date first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    checkOutInput.setText("${month + 1}/$day/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.datePicker.minDate = checkInDateMillis!! + 86400000

            datePicker.show()
        }
        val saveLodgingButton = view.findViewById<Button>(R.id.saveLodgingButton)

        lodgingId = arguments?.getInt("lodging_id", 0) ?: 0
        showId = arguments?.getInt("show_id", 0) ?: 0

        val hotelName = arguments?.getString("hotel_name") ?: ""
        val address = arguments?.getString("address") ?: ""
        val checkIn = arguments?.getString("check_in") ?: ""
        val checkOut = arguments?.getString("check_out") ?: ""

        hotelNameInput.setText(hotelName)
        addressInput.setText(address)
        checkInInput.setText(checkIn)
        checkOutInput.setText(checkOut)

        saveLodgingButton.setOnClickListener {
            val updatedHotelName = hotelNameInput.text.toString().trim()
            val updatedAddress = addressInput.text.toString().trim()
            val updatedCheckIn = checkInInput.text.toString().trim()
            val updatedCheckOut = checkOutInput.text.toString().trim()

            if (updatedHotelName.isEmpty() || updatedAddress.isEmpty() ||
                updatedCheckIn.isEmpty() || updatedCheckOut.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
            dateFormat.isLenient = false

            val checkInDate = try {
                dateFormat.parse(updatedCheckIn)
            } catch (e: Exception) {
                null
            }

            val checkOutDate = try {
                dateFormat.parse(updatedCheckOut)
            } catch (e: Exception) {
                null
            }

            if (checkInDate == null || checkOutDate == null) {
                Toast.makeText(requireContext(), "Enter valid dates in MM/DD/YYYY format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (checkOutDate.before(checkInDate)) {
                Toast.makeText(requireContext(), "Check-out cannot be before check-in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val lodging = Lodging(
                id = lodgingId,
                showId = showId,
                hotelName = updatedHotelName,
                address = updatedAddress,
                checkIn = updatedCheckIn,
                checkOut = updatedCheckOut
            )

            if (lodgingId == 0) {
                lodgingViewModel.insert(lodging)
            } else {
                lodgingViewModel.update(lodging)
            }

            parentFragmentManager.popBackStack()
        }
    }
}