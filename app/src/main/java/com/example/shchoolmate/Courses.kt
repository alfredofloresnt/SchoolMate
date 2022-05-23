package com.example.shchoolmate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.shchoolmate.databinding.FragmentCoursesBinding
import kotlinx.coroutines.launch

class Courses : Fragment() {

    //private val viewModel: CoursesViewModel by activityViewModels()
    private var _binding: FragmentCoursesBinding? = null
    private val binding get () = _binding!!

    private val viewModel: CoursesViewModel by activityViewModels {
        CoursesViewModelFactory(
            (activity?.application as MyInitApp).database.courseDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_courses, container, false)
        _binding =  FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.insertCourse(Course(0, name = "My course 4", location = "a random ZOOM link 4"))
            viewModel.insertCourse(Course(0, name = "My course 5", location = "a random ZOOM link 5"))
            binding.textCoursesCount.text = viewModel.getAllCourses().size.toString()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}