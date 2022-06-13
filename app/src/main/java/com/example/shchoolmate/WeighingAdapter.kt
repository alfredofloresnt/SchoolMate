package com.example.shchoolmate

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.shchoolmate.databinding.CalculateScoreItemBinding
import kotlinx.coroutines.launch
import java.lang.ref.Reference


class WeighingAdapter (val context: Context?, var activities: List<ActivityScore>, var weight: Double, var course: Course, var viewModel: CoursesViewModel, var lifecycleScope: LifecycleCoroutineScope, var calculate: (course: Course) -> Unit): RecyclerView.Adapter<WeighingAdapter.ViewHolder>() {
    class ViewHolder(val binding: CalculateScoreItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CalculateScoreItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.apply {
            textName.text = activities[position].name + "(" + ((weight * 100.0).toInt()).toString() + "%" + ")"
            editWeight.setText(activities[position].weight.toString())
            itemListener(holder, activities[position])

            //textValue.text = activities[position].weight
        }



    }

    fun itemListener(holder: ViewHolder, activity: ActivityScore) {
        holder.binding.editWeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(!s.isEmpty())
                    try
                    {

                        var value = s.toString().toDouble();
                        lifecycleScope.launch {
                            //Toast.makeText(context, "Hello " + activity.id.toString() + " " + value.toString() , Toast.LENGTH_SHORT).show()
                            viewModel.updateActivityWeight(activity.id, value)
                            calculate(course)
                        }
                        // it means it is double
                    } catch (e1: Exception) {
                        // this means it is not double
                        //Toast.makeText(holde, "Not valid", Toast.LENGTH_SHORT).show()
                    }

            }
        })
    }


    override fun getItemCount(): Int {
        return activities.size
    }

}