package com.example.schoolmate

import android.graphics.Color
import android.icu.util.Calendar
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shchoolmate.Hour
import com.example.shchoolmate.databinding.HourBinding

class HourAdapter (var hours: List<Hour>): RecyclerView.Adapter<HourAdapter.ViewHolder>() {

    class ViewHolder(val binding: HourBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HourBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val i = hours[position].hour
            hourText.text = "$i:00"
        }
    }

    override fun getItemCount(): Int {
        return hours.size
    }
}