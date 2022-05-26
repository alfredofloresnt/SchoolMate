package com.example.shchoolmate

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolmate.HourAdapter
import com.example.shchoolmate.databinding.FragmentCoursesBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

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

    fun sameDay(c1 : Calendar, c2: Calendar) : Boolean {
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH)

    }

    fun updateDays(){
        val week : Calendar = Calendar.getInstance()
        week.set(Calendar.WEEK_OF_YEAR, viewModel.activeWeek)

        week.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        binding.sunday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.sunday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)


        week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        binding.monday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.monday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)


        week.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        binding.tuesday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.tuesday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.tuesday.setBackgroundColor(if (sameDay(week, viewModel.activeDate)) Color.YELLOW else Color.TRANSPARENT)


        week.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        binding.wednesday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.wednesday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)

        week.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        binding.thursday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.thursday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)


        week.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        binding.friday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.friday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)


        week.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        binding.saturday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.saturday.setTextColor( if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)

    }

    fun updateTitle(){
        val date : Calendar = Calendar.getInstance()
        date.set(Calendar.WEEK_OF_YEAR, viewModel.activeWeek)
        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val month_date = SimpleDateFormat("MMM")
        val month_name: String = month_date.format(date.getTime())
        val title : String = month_name + " ${date.get(Calendar.YEAR)}"
        binding.monthYearTV.setText(title)
    }

    fun updateDate(){
        updateTitle()
        updateDays()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            //viewModel.insertCourse(Course(0, name = "My course 4", location = "a random ZOOM link 4"))
            //viewModel.insertCourse(Course(0, name = "My course 5", location = "a random ZOOM link 5"))
            // binding.textCoursesCount.text = viewModel.getAllCourses().size.toString()
        }

        (activity as AppCompatActivity).supportActionBar?.title = "Courses"

        binding.back.setOnClickListener {
            viewModel.prevWeek()
            updateDate()
        }
        binding.next.setOnClickListener {
            viewModel.nextWeek()
            updateDate()
        }

        updateDate()

        val adapter = HourAdapter((0..23).map { Hour(it) })
        binding.dayRecyclerView.adapter = adapter
        binding.dayRecyclerView.layoutManager = LinearLayoutManager(activity)
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}