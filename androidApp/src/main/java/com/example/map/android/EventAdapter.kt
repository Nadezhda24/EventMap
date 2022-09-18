package com.example.map.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private val data: List<Event>):
    RecyclerView.Adapter<EventAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
        var description: TextView = itemView.findViewById(R.id.Description)
        var title: TextView = itemView.findViewById(R.id.Title)
        var author: TextView = itemView.findViewById(R.id.Author)
        var category: TextView = itemView.findViewById(R.id.Category)
        var date: TextView = itemView.findViewById(R.id.Date)
        var address: TextView = itemView.findViewById(R.id.Address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.description.visibility = View.GONE
        holder.title.text = data[position].title
        holder.author.text = data[position].author
        holder.category.text = data[position].category?.name
        holder.date.text = data[position].date
        holder.address.text = data[position].address
    }

    override fun getItemCount() = data.size


}