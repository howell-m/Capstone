package com.example.showpilot.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.showpilot.R
import com.example.showpilot.data.AppDatabase
import com.example.showpilot.data.repository.ShowRepository
import com.example.showpilot.viewmodel.ShowViewModel
import com.example.showpilot.viewmodel.ShowViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeShowAdapter: HomeShowAdapter
    private lateinit var showViewModel: ShowViewModel
    private lateinit var viewAllText: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewShows)
        viewAllText = view.findViewById(R.id.viewAll)

        homeShowAdapter = HomeShowAdapter(mutableListOf()) { show ->

        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = homeShowAdapter

        val dao = AppDatabase.getDatabase(requireContext()).showDao()
        val repository = ShowRepository(dao)
        val factory = ShowViewModelFactory(repository)
        showViewModel = ViewModelProvider(this, factory)[ShowViewModel::class.java]

        showViewModel.allShows.observe(viewLifecycleOwner) { shows ->
            homeShowAdapter.updateShows(shows.take(3))
        }

        viewAllText.setOnClickListener {
            requireActivity()
                .findViewById<BottomNavigationView>(R.id.bottomNav)
                .selectedItemId = R.id.showDetailsFragment
        }
    }
}