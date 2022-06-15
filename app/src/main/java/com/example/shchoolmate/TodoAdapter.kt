package com.example.shchoolmate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shchoolmate.databinding.ItemTodosBinding

class TodoAdapter (var todos: List<Todo>): RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemTodosBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodosBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tituloRecycle.text = todos[position].title
            commentRecycle.text = todos[position].comments
            dateRecycle.text = todos[position].date
        }

    }

    override fun getItemCount(): Int {
        return todos.size
    }
}