package com.example.myneighborly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myneighborly.HelpType
import com.example.myneighborly.R

class HelpTypesAdapter(private val helpTypes: List<HelpType>) :
    RecyclerView.Adapter<HelpTypesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.helpTypeImage)
        val textView: TextView = view.findViewById(R.id.helpTypeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_help_type, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = helpTypes[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textView.text = item.title
    }

    override fun getItemCount() = helpTypes.size
}
