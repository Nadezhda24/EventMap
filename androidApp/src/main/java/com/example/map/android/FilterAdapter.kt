package com.example.map.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.map.android.Models.Category

class FilterAdapter(private val data: List<Category>):
    RecyclerView.Adapter<FilterAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.filter_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.checkbox.text = data[position].name
        holder.checkbox.isChecked = data[position].isCheck

        holder.checkbox.setOnClickListener{
            for(i in 0 until data.size){
                if (i != position ) data[i].isCheck = false
            }
            data[position].isCheck = !data[position].isCheck
            notifyDataSetChanged()
        }

    }

    override fun getItemCount() = data.size


}