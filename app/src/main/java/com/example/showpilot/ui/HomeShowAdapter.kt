package com.example.showpilot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.showpilot.R
import com.example.showpilot.data.entities.Show

class HomeShowAdapter(
    private var shows: MutableList<Show>,
    private val onShowClick: (Show) -> Unit
) : RecyclerView.Adapter<HomeShowAdapter.HomeShowViewHolder>() {

    inner class HomeShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVenue: TextView = itemView.findViewById(R.id.tvVenue)
        val tvCity: TextView = itemView.findViewById(R.id.tvCity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home_show, parent, false)
        return HomeShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeShowViewHolder, position: Int) {
        val show = shows[position]

        holder.tvVenue.text = show.venue
        holder.tvCity.text = show.city

        holder.itemView.setOnClickListener {
            onShowClick(show)
        }
    }

    override fun getItemCount(): Int = shows.size

    fun updateShows(newShows: List<Show>) {
        shows.clear()
        shows.addAll(newShows)
        notifyDataSetChanged()
    }
}