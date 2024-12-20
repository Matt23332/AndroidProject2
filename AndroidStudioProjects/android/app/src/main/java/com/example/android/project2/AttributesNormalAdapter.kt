package com.example.androidproject2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R

class AttributesNormalAdapter(val context: Context, val allData : List<Map<String, Any>>): RecyclerView.Adapter<AttributesNormalAdapter.AttributesNormalViewHolder>() {
    class AttributesNormalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val normalAttributeTitle: TextView = itemView.findViewById(R.id.normalAttributeTitle)
        val normalAttributeValue: TextView = itemView.findViewById(R.id.normalAttributeValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttributesNormalViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_normal_attributes_ecomm, parent, false)
        return AttributesNormalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: AttributesNormalViewHolder, position: Int) {
        val currentData = allData[position]
        for((key, value) in currentData){
            holder.normalAttributeTitle.text = key.toString() + " - "
            holder.normalAttributeValue.text = value.toString()
        }
    }
}