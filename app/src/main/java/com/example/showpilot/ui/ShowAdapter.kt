package com.example.showpilot.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.showpilot.R
import com.example.showpilot.data.entities.Lodging
import com.example.showpilot.data.entities.Show
import com.example.showpilot.data.entities.Expense

class ShowAdapter(
    private var shows: MutableList<Show>,
    private val listener: OnShowActionListener
) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {

    private var lodgingMap: Map<Int, Lodging> = emptyMap()
    private var expenseByShow: Map<Int, List<Expense>> = emptyMap()

    interface OnShowActionListener {
        fun onEdit(show: Show)
        fun onDelete(show: Show)
        fun onEditLodging(show: Show)
        fun onAddExpense(show: Show)
    }

    inner class ShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvVenue: TextView = itemView.findViewById(R.id.tvVenue)
        val tvCity: TextView = itemView.findViewById(R.id.tvCity)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)
        val tvLodgingInfo: TextView = itemView.findViewById(R.id.tvLodgingInfo)
        val tvEditLodging: TextView = itemView.findViewById(R.id.tvEditLodging)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_show, parent, false)
        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = shows[position]

        holder.tvVenue.text = show.venue
        holder.tvCity.text = show.city
        holder.tvDate.text = show.date
        holder.tvTime.text = show.time

        val lodging = lodgingMap[show.id]
        if (lodging != null) {
            holder.tvLodgingInfo.text =
                "${lodging.hotelName} • ${lodging.address}\n${lodging.checkIn} - ${lodging.checkOut}"
            holder.tvEditLodging.text = "Edit"
        } else {
            holder.tvLodgingInfo.text = "No lodging added"
            holder.tvEditLodging.text = "+ Add"
        }


        holder.btnEdit.setOnClickListener {
            listener.onEdit(show)
        }

        holder.btnDelete.setOnClickListener {
            listener.onDelete(show)
        }

        holder.tvEditLodging.setOnClickListener {
            listener.onEditLodging(show)
        }

    }

    override fun getItemCount(): Int = shows.size

    fun updateShows(newShows: List<Show>) {
        shows.clear()
        shows.addAll(newShows)
        notifyDataSetChanged()
    }

    fun updateLodgings(lodgings: List<Lodging>) {
        lodgingMap = lodgings.associateBy { it.showId }
        notifyDataSetChanged()
    }

    fun updateExpense(expense: List<Expense>) {
        expenseByShow = expense.groupBy { it.showId }
        notifyDataSetChanged()
    }
    fun getLodgingForShow(showId: Int): Lodging? {
        return lodgingMap[showId]
    }
}