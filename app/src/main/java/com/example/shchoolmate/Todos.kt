package com.example.shchoolmate

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import android.widget.Toast
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

        updateTodo()

        binding.btntodo.setOnClickListener {
            viewAlert()
        }
    }

    fun updateTodo(){
        lifecycleScope.launch{
            val adapter = TodoAdapter(viewModel.getAllTodos())
            binding.rvTodos.adapter=adapter
            binding.rvTodos.layoutManager= LinearLayoutManager(activity)
        }
    }
    fun viewAlert() {
        val builder = AlertDialog.Builder(activity)
        val inflater = layoutInflater
        builder.setTitle("Add Task")
        val dialogLayout = inflater.inflate(R.layout.modal_todos, null)
        val editTextTitle  = dialogLayout.findViewById<EditText>(R.id.titulo_modal)
        val editTextComment = dialogLayout.findViewById<EditText>(R.id.modal_coment)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Add") { dialogInterface, i ->
            lifecycleScope.launch {
                val titulo = editTextTitle.text.toString()
                val comment = editTextComment.text.toString()
                val date = getCurrentDateTime()
                val dateInString = date.toString("yyyy/MM/dd")
                lifecycleScope.launch {
                    viewModel.insertTodo(Todo(0,titulo,comment,dateInString))
                    updateTodo()
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i -> Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show() }
        builder.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}