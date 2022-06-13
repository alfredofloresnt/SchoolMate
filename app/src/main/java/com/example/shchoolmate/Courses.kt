package com.example.shchoolmate

import OnSwipeTouchListener
import android.content.Context
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.schoolmate.HourAdapter
import com.example.shchoolmate.databinding.FragmentCoursesBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class Courses : Fragment() {

    private var _binding: FragmentCoursesBinding? = null
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
        binding.sunday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.sunday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)


        week.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        binding.monday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.monday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.monday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)


        week.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
        binding.tuesday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.tuesday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.tuesday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)


        week.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
        binding.wednesday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.wednesday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.wednesday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)

        week.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        binding.thursday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.thursday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.thursday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)

        week.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        binding.friday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.friday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.friday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)


        week.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        binding.saturday.setText(week.get(Calendar.DAY_OF_MONTH).toString())
        binding.saturday.setTextColor( if (sameDay(week, viewModel.activeDate)) Color.WHITE else if (sameDay(week, viewModel._today)) Color.BLACK else Color.GRAY)
        binding.saturday.setBackgroundResource(if (sameDay(week, viewModel.activeDate)) R.drawable.rounded_textview else 0)

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

    fun coursesToHours(courses : List<Course>) : List<Hour> {
        val hours = (0..23).map { it -> Hour(it,null,null, null) }

        for(course in courses){
            var curHour = course.initialHour
            var curMin = course.initialMin

            val endHour = course.endHour
            val endMin = course.endMin

            var first = true
            // c++

            while((curHour*60 + curMin) < (endHour*60 + endMin)){

                if(curMin == 0){
                    hours[curHour].down = true
                    hours[curHour].title = if (first) course.name else ""
                    curMin = 30
                    if (first) {
                        first = false
                    }
                }else if(curMin == 30) {
                    hours[curHour + 1].up = true
                    hours[curHour + 1].title = if (first) course.name else ""
                    curHour++
                    curMin = 0
                }

            }

        }

        return hours

    }

    fun updateHours(){
        val adapter = HourAdapter(coursesToHours(viewModel.todayCourses))
        binding.dayRecyclerView.adapter = adapter
        binding.dayRecyclerView.layoutManager = LinearLayoutManager(activity)
        val h : Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        binding.dayRecyclerView.smoothScrollToPosition(if (h+5 < 24) h+5 else 24);
    }

    fun onClickDay(day : Int){
        val date = Calendar.getInstance()
        date.set(Calendar.WEEK_OF_YEAR, viewModel.activeWeek)
        date.set(Calendar.DAY_OF_WEEK, day)
        viewModel.updateActiveDate(date)
        updateDate()
        updateHours()
        //val toast = Toast.makeText(activity, "picaste en ${day}", Toast.LENGTH_SHORT)
        // toast.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            var courses = viewModel.getAllCourses()
            viewModel.updateCourses(courses)
            viewModel.updateTodayCourses()
            updateHours()
        }

        binding.header.setOnTouchListener(object : OnSwipeTouchListener(activity as Context) {

            override fun onSwipeLeft() { // right xd

                viewModel.nextWeek()
                updateDate()
                updateHours()
            }

            override fun onSwipeRight() { // go left xd

                viewModel.prevWeek()
                updateDate()
                updateHours()
            }
        })


        binding.dayRecyclerView.setOnTouchListener(object : OnSwipeTouchListener(activity as Context) {

            override fun onSwipeLeft() { // right xd

                viewModel.nextDay()
                updateDate()
                updateHours()

            }

            override fun onSwipeRight() { // go left xd

                viewModel.prevDay()
                updateDate()
                updateHours()
            }
        })

        binding.next.setOnClickListener {
            viewModel.nextWeek()
            updateDate()
            updateHours()

        }

        binding.back.setOnClickListener {
            viewModel.prevWeek()
            updateDate()
            updateHours()
        }


        binding.sunday.setOnClickListener {
            onClickDay(Calendar.SUNDAY)
        }
        binding.monday.setOnClickListener {
            onClickDay(Calendar.MONDAY)
        }
        binding.tuesday.setOnClickListener{
            onClickDay(Calendar.TUESDAY)
        }
        binding.wednesday.setOnClickListener{
            onClickDay(Calendar.WEDNESDAY)
        }
        binding.thursday.setOnClickListener{
            onClickDay(Calendar.THURSDAY)
        }
        binding.friday.setOnClickListener{
            onClickDay(Calendar.FRIDAY)
        }
        binding.saturday.setOnClickListener{
            onClickDay(Calendar.SATURDAY)
        }

        updateDate()
        updateHours()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}