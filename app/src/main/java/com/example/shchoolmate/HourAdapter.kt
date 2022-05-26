package com.example.schoolmate

import android.graphics.Color
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
            hourText.text = if (i < 10) "0$i:00" else "$i:00"
            if(i == 10){
                courseAbove.setText("Programacion Avanzada")
                courseBelow.setText("")
                courseAbove.setBackgroundColor(Color.rgb(255	,221	,110))
                courseBelow.setBackgroundColor(Color.rgb(255	,221	,110))
                divider.setBackgroundColor(Color.rgb(255	,221	,110))
            }else{
                courseAbove.setText("")
                courseBelow.setText("")
                courseAbove.setBackgroundColor(Color.TRANSPARENT)
                courseBelow.setBackgroundColor(Color.TRANSPARENT)
                divider.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    override fun getItemCount(): Int {
        return hours.size
    }
}