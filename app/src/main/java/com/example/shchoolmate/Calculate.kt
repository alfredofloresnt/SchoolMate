package com.example.shchoolmate

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shchoolmate.databinding.FragmentCalculateBinding
import kotlinx.coroutines.launch
import java.lang.Exception


class Calculate : Fragment() {
    //private val viewModel: CoursesViewModel by activityViewModels()
    private var _binding: FragmentCalculateBinding? = null
    private val binding get () = _binding!!

    private val viewModel: CoursesViewModel by activityViewModels {
        CoursesViewModelFactory(
            (activity?.application as MyInitApp).database.courseDao(),
            (activity?.application as MyInitApp).database.weightingDao()
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
            updateCourse(courses[selectedCourseIndex])
            binding.buttonNextCourse.setOnClickListener {
                selectedCourseIndex = (selectedCourseIndex + 1) % courses.size
                updateCourse(courses[selectedCourseIndex])
            }
            binding.buttonBackCourse.setOnClickListener {
                selectedCourseIndex = (selectedCourseIndex - 1 % courses.size + courses.size) % courses.size
                updateCourse(courses[selectedCourseIndex])
            }
            binding.btnAddExam.setOnClickListener{
                viewAlert(courses[selectedCourseIndex], "Exams")
            }


        }





    }

    fun viewAlert(course: Course, type: String) {
        val builder = AlertDialog.Builder(activity)
        val inflater = layoutInflater
        builder.setTitle("With EditText")
        val dialogLayout = inflater.inflate(R.layout.dialog_add_activity, null)
        val editTextName  = dialogLayout.findViewById<EditText>(R.id.editName)
        val editTextWeight = dialogLayout.findViewById<EditText>(R.id.editWeight1)
        builder.setView(dialogLayout)
        builder.setPositiveButton("Save") { dialogInterface, i ->
            lifecycleScope.launch {
                Toast.makeText(context, "EditTextName is " + editTextName.text.toString(), Toast.LENGTH_SHORT).show()
                var weighing = viewModel.getWeighingsByIdCourseAndName(course.id, type)
                viewModel.insertActivityScore(ActivityScore(0, weighing.id, editTextName.text.toString(), 0.0, editTextWeight.text.toString().toDouble()))
                Toast.makeText(context, "idWeighing " + weighing.id.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, i -> Toast.makeText(context, "EditText is " + editTextWeight.text.toString(), Toast.LENGTH_SHORT).show() }
        builder.show()
    }


    fun updateCourse(course: Course){
        var weighings:List<Weighing>
        var activitiesExams:List<ActivityScore>
        lifecycleScope.launch {
            var examsWeight = viewModel.getWeighingsByIdCourseAndName(course.id, "Exams")

            binding.editTextExamWeight.setText(examsWeight.value.toString())

            weighings = viewModel.getWeighingsByIdCourse(course.id)

            examsWeightListener(examsWeight)

            Toast.makeText(context, weighings.size.toString(), Toast.LENGTH_SHORT).show()
            activitiesExams = viewModel.getActivityScoreByIdWeighing(course.id, "Exams")
            val weight: Double = 1.0 / activitiesExams.size
            //var mLayout = binding.layoutExams
            val adapter = WeighingAdapter(context, activitiesExams, weight, course, viewModel, lifecycleScope, ::calculateExams)
            calculateExams(course)
            binding.rvExams.adapter = adapter
            binding.rvExams.layoutManager = LinearLayoutManager(activity)

        }
        binding.textCourseSelected.text = course.name
    }

     fun examsWeightListener(examsWeight: Weighing) {
        binding.editTextExamWeight.addTextChangedListener(object : TextWatcher {
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
                            viewModel.updateWeight(examsWeight.id, value)
                        }
                        // it means it is double
                    } catch (e1: Exception) {
                        // this means it is not double
                        Toast.makeText(context, "Not valid", Toast.LENGTH_SHORT).show()
                    }

            }
        })
    }

    fun calculateExams(course: Course) {
        lifecycleScope.launch{
            var examsWeight = viewModel.getWeighingsByIdCourseAndName(course.id, "Exams")
            var activitiesExams = viewModel.getActivityScoreByIdWeighing(1, "Exams")
            val weight: Double = 1.0 / activitiesExams.size
            var total = 0.0
            for (item in activitiesExams) {
                total += item.weight * weight
            }
            total = total * (examsWeight.value / 100)
            binding.textTotal.text = total.toString()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}