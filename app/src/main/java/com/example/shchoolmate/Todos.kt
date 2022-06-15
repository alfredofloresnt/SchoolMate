package com.example.shchoolmate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shchoolmate.databinding.FragmentCoursesBinding
import com.example.shchoolmate.databinding.FragmentTodosBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

class Todos : Fragment() {
    private var _binding: FragmentTodosBinding? = null
    private val binding get () = _binding!!

    private val viewModel: TodoViewModel by activityViewModels {
        TodosViewModelFactory(
            (activity?.application as MyInitApp).database.todoDao()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =  FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch{
            val adapter = TodoAdapter(viewModel.getAllTodos())
            binding.rvTodos.adapter=adapter
            binding.rvTodos.layoutManager= LinearLayoutManager(activity)
        }


        binding.btntodo.setOnClickListener {
            val titulo = binding.titulo.text.toString()
            val comment = binding.coment.text.toString()
            val date = getCurrentDateTime()
            val dateInString = date.toString("yyyy/MM/dd")
            lifecycleScope.launch {


                viewModel.insertTodo(Todo(0,titulo,comment,dateInString))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}