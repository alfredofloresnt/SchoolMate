package com.example.shchoolmate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.shchoolmate.databinding.FragmentCalculateBinding
import com.example.shchoolmate.databinding.FragmentCoursesBinding
import kotlinx.coroutines.launch


class Calculate : Fragment() {
    //private val viewModel: CoursesViewModel by activityViewModels()
    private var _binding: FragmentCalculateBinding? = null
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
        _binding =  FragmentCalculateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selectedCourseIndex = 0
        lifecycleScope.launch {
            var courses = viewModel.getAllCourses()
            //viewModel.insertCourse(Course(0, name = "My course 1", location = "a random ZOOM link"))
            updateCourse(courses, selectedCourseIndex)
            binding.buttonNextCourse.setOnClickListener {
                selectedCourseIndex = (selectedCourseIndex + 1) % courses.size
                updateCourse(courses, selectedCourseIndex)
            }
            binding.buttonBackCourse.setOnClickListener {
                selectedCourseIndex = (selectedCourseIndex - 1 % courses.size + courses.size) % courses.size
                updateCourse(courses, selectedCourseIndex)
            }
        }

        for (i in 1..5) {
            val dynamicTextview = TextView(activity)
            //val score
            dynamicTextview.text = "Dynamic text"
            binding.calculateLinearLayout.addView(dynamicTextview)
        }

    }

    fun updateCourse(courses: List<Course>, index: Int){
        binding.textCourseSelected.text = courses[index].name
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}