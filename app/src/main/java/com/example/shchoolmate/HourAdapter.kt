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
            val up = hours[position].up
            val down = hours[position].down
            val title = hours[position].title

            hourText.text = if (i < 10) "0$i:00" else "$i:00"

            val yellow = Color.rgb(255	,255	,159)

            courseAbove.setBackgroundColor(if (up == true) yellow else Color.TRANSPARENT)
            courseBelow.setBackgroundColor(if (down == true) yellow else Color.TRANSPARENT)
            divider.setBackgroundColor(if (up == true || down == true) yellow else Color.LTGRAY)
            courseAbove.setText(if(up == true && title != null) title else "")
            courseBelow.setText(if(down == true && title != null && (up == null || up == false)) title else "")

        }
    }

    override fun getItemCount(): Int {
        return hours.size
    }
}