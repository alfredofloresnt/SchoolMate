package com.example.shchoolmate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.shchoolmate.databinding.FragmentAddBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class Add : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get () = _binding!!

    private val viewModel: CoursesViewModel by activityViewModels {
        CoursesViewModelFactory(
            (activity?.application as MyInitApp).database.courseDao(),
            (activity?.application as MyInitApp).database.weightingDao()
        )
    }

    data class myDate(var minute: Int, var hour: Int,var day: Int, var month: Int, var year: Int)

    var startDate  = myDate(-1,-1,-1,-1,-1)
    var endDate = myDate(-1,-1,-1,-1,-1)

    fun formatTime(h : Int, m : Int) : String {
        if(h<10) {
            if(m<10){
                return "0$h:0$m"
            }else{
                return "0$h:$m"
            }
        }else{
            if(m<10){
                return "$h:0$m"
            }else{
                return "$h:$m"
            }
        }
    }

    fun formatDate(y: Int, m : Int, d: Int) : String {
        val month_date = SimpleDateFormat("MMM")
        val date : Calendar = Calendar.getInstance()
        date.set(Calendar.MONTH, m)
        val month_name: String = month_date.format(date.time)
        val title : String = month_name + " $d, $y"
        return title
    }

    fun handleSetTime(h: Int, m: Int, start: Boolean){
        if(m != 0 && m !=30){ // only possible minutes
            Toast.makeText(activity, "Only full and half hours please", Toast.LENGTH_SHORT).show()
        }else{
            if(start){
                binding.startTime.setText(formatTime(h,m))
                startDate.minute = m
                startDate.hour = h
            }else{
                if(startDate.minute + startDate.hour*60 >= m + h*60){
                    Toast.makeText(activity, "Make sure end time is after start time", Toast.LENGTH_SHORT).show()
                }else{
                    binding.endTime.setText(formatTime(h,m))
                    endDate.minute = m
                    endDate.hour = h
                }
            }
        }


    }

    fun handleTime(start: Boolean){
        val cldr: Calendar = Calendar.getInstance()
        val hour: Int = cldr.get(Calendar.HOUR_OF_DAY)
        val minutes: Int = cldr.get(Calendar.MINUTE)
        if(start){
            val picker = TimePickerDialog(activity,
                { _, sHour, sMinute ->  handleSetTime(sHour, sMinute, start)}, hour, minutes, true
            )
            picker.show()
        }else{
            if(startDate.minute == -1 && startDate.hour == -1){
                Toast.makeText(activity, "Please enter start time first", Toast.LENGTH_SHORT).show()
            }else{
                val picker = TimePickerDialog(activity,
                    { _, sHour, sMinute -> handleSetTime(sHour, sMinute, start) }, startDate.hour, startDate.minute, true
                )
                picker.show()
            }
        }
    }

    fun handleSetDate(y: Int, m: Int, d:Int, start: Boolean){
        if(start){
            binding.startDate.setText(formatDate(y,m,d))
            startDate.year = y
            startDate.month = m
            startDate.day = d
        }else{
            if(startDate.year*365 + startDate.month*30 + startDate.day >= y*365 + m*30 +d){
                Toast.makeText(activity, "Make sure end time is after start date", Toast.LENGTH_SHORT).show()
            }else{
                binding.endDate.setText(formatDate(y,m,d))
                endDate.year = y
                endDate.month = m
                endDate.day = d
            }
        }
    }
    fun clean(){
        startDate  = myDate(-1,-1,-1,-1,-1)
        endDate = myDate(-1,-1,-1,-1,-1)
        binding.editTextTitulo.setText("")
        binding.editTextLocation.setText("")
        binding.startDate.setText("")
        binding.startTime.setText("")
        binding.endDate.setText("")
        binding.endTime.setText("")
        binding.sunday.isChecked =  false
        binding.monday.isChecked = false
        binding.tuesday.isChecked = false
        binding.wednesday.isChecked = false
        binding.thursday.isChecked = false
        binding.friday.isChecked = false
        binding.saturday.isChecked = false
    }

    fun handleDate(start: Boolean){
        val cldr: Calendar = Calendar.getInstance()
        val yNow: Int = cldr.get(Calendar.YEAR)
        val mNow: Int = cldr.get(Calendar.MONTH)
        val dNow: Int = cldr.get(Calendar.DAY_OF_MONTH)

        if(start){
            val picker = DatePickerDialog(activity as Context, {_, y,m,d -> handleSetDate(y,m,d,start)}, yNow, mNow, dNow)
            picker.show()
        }else{
            if(startDate.year == -1 && startDate.month == -1 && startDate.day ==-1){
                Toast.makeText(activity, "Please enter start date first", Toast.LENGTH_SHORT).show()

            }else{
                val picker = DatePickerDialog(activity as Context, {_,y,m,d -> handleSetDate(y,m,d,start)}, startDate.year,startDate.month,startDate.day)
                picker.show()
            }
        }
    }

    fun handleSubmit()  {
        val titulo = binding.editTextTitulo.text.toString()
        val location = binding.editTextLocation.text.toString()
        val daysArray = arrayOf<Boolean>(binding.sunday.isChecked,binding.monday.isChecked,binding.tuesday.isChecked,
            binding.wednesday.isChecked,binding.thursday.isChecked,binding.friday.isChecked,
            binding.saturday.isChecked)


        if(titulo == "" || location == ""){
            Toast.makeText(activity, "Please fill all the text fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (startDate.year == -1 || startDate.month == -1 || startDate.day == -1 || startDate.hour == -1 || startDate.minute == -1
            || endDate.year == -1 || endDate.month == -1 || endDate.day == -1 || endDate.hour == -1 || endDate.minute == -1
        ) {
            Toast.makeText(activity, "Please fill all the time and date fields", Toast.LENGTH_SHORT).show()
            return
        }

        var checked : Boolean = false

        for(i in daysArray.indices){
            if(daysArray[i]){
                checked = true
            }
        }

        if(!checked){
            Toast.makeText(activity, "Please check at least one day", Toast.LENGTH_SHORT).show()
            return
        }

        // insertar curso
        Toast.makeText(activity, "Adding course", Toast.LENGTH_SHORT).show()
        // finish()

        val course  = Course(0, titulo, location, startDate.hour, startDate.minute, endDate.hour, endDate.minute,
            startDate.year, startDate.month, startDate.day, endDate.year, endDate.month, endDate.day,
            daysArray[0], daysArray[1], daysArray[2],daysArray[3], daysArray[4], daysArray[5], daysArray[6])

        clean()

        lifecycleScope.launch {
            val id = viewModel.insertCourse(course)
            var idW = viewModel.insertWeighing(Weighing(0, id.toInt(), "Exams", 0.0))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_courses, container, false)
        _binding =  FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onCreate(savedInstanceState)

        binding.addButton.setOnClickListener {
            handleSubmit()
        }

        binding.startTime.inputType
        binding.startTime.setOnClickListener{
            handleTime(true)
        }
        binding.endTime.setOnClickListener{
            handleTime(false)
        }
        binding.startDate.setOnClickListener{
            handleDate(true)
        }
        binding.endDate.setOnClickListener{
            handleDate(false)
        }
    }


}