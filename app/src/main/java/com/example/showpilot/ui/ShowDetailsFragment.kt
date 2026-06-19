package com.example.showpilot.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showpilot.R
import com.example.showpilot.data.AppDatabase
import com.example.showpilot.data.entities.Show
import com.example.showpilot.data.repository.ShowRepository
import com.example.showpilot.viewmodel.ExpenseViewModel
import com.example.showpilot.viewmodel.LodgingViewModel
import com.example.showpilot.viewmodel.ShowViewModel
import com.example.showpilot.viewmodel.ShowViewModelFactory

class ShowDetailsFragment : Fragment(R.layout.fragment_show_details) {

    private lateinit var viewModel: ShowViewModel
    private lateinit var lodgingViewModel: LodgingViewModel
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var adapter: ShowAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var addShowButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewShows)
        addShowButton = view.findViewById(R.id.addShowButton)

        val dao = AppDatabase.getDatabase(requireContext()).showDao()
        val repository = ShowRepository(dao)
        val factory = ShowViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ShowViewModel::class.java]

        lodgingViewModel = ViewModelProvider(this)[LodgingViewModel::class.java]
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        adapter = ShowAdapter(mutableListOf(), object : ShowAdapter.OnShowActionListener {

            override fun onEdit(show: Show) {
                val bundle = Bundle().apply {
                    putInt("SHOW_ID", show.id)
                    putString("SHOW_VENUE", show.venue)
                    putString("SHOW_CITY", show.city)
                    putString("SHOW_DATE", show.date)
                    putString("SHOW_TIME", show.time)
                }

                findNavController().navigate(
                    R.id.action_showDetailsFragment_to_addShowFragment,
                    bundle
                )
            }

            override fun onDelete(show: Show) {
                viewModel.delete(show)
            }

            override fun onEditLodging(show: Show) {
                val lodging = adapter.getLodgingForShow(show.id)

                val bundle = Bundle().apply {
                    putInt("show_id", show.id)

                    if (lodging != null) {
                        putInt("lodging_id", lodging.id)
                        putString("hotel_name", lodging.hotelName)
                        putString("address", lodging.address)
                        putString("check_in", lodging.checkIn)
                        putString("check_out", lodging.checkOut)
                    }
                }

                findNavController().navigate(
                    R.id.action_showDetailsFragment_to_addEditLodging,
                    bundle
                )
            }
            override fun onAddExpense(show: Show) {
                val bundle = Bundle().apply {
                    putInt("show_id", show.id)
                }

                findNavController().navigate(
                    R.id.action_showDetailsFragment_to_addEditExpense,
                    bundle
                )
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.allShows.observe(viewLifecycleOwner) { shows ->
            adapter.updateShows(shows)
        }

        lodgingViewModel.allLodgings.observe(viewLifecycleOwner) { lodgings ->
            adapter.updateLodgings(lodgings)
        }


        addShowButton.setOnClickListener {
            findNavController().navigate(R.id.action_showDetailsFragment_to_addShowFragment)
        }
    }
}