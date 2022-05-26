package com.example.shchoolmate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shchoolmate.databinding.ActivityAddBinding
import java.text.SimpleDateFormat


class AddAct : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    data class myDate(var minute: Int, var hour: Int,var day: Int, var month: Int, var year: Int)

    val startDate  = myDate(-1,-1,-1,-1,-1)
    val endDate = myDate(-1,-1,-1,-1,-1)

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
            Toast.makeText(this, "Only full and half hours please", Toast.LENGTH_SHORT).show()
        }else{
            if(start){
                binding.startTime.setText(formatTime(h,m))
                startDate.minute = m
                startDate.hour = h
            }else{
                if(startDate.minute + startDate.hour*60 >= m + h*60){
                    Toast.makeText(this, "Make sure end time is after start time", Toast.LENGTH_SHORT).show()
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
             val picker = TimePickerDialog(this,
                { _, sHour, sMinute ->  handleSetTime(sHour, sMinute, start)}, hour, minutes, true
            )
            picker.show()
        }else{
            if(startDate.minute == -1 && startDate.hour == -1){
                Toast.makeText(this, "Please enter start time first", Toast.LENGTH_SHORT).show()
            }else{
                val picker = TimePickerDialog(this,
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
                Toast.makeText(this, "Make sure end time is after start date", Toast.LENGTH_SHORT).show()
            }else{
                binding.endDate.setText(formatDate(y,m,d))
                endDate.year = y
                endDate.month = m
                endDate.day = d
            }
        }
    }

    fun handleDate(start: Boolean){
        val cldr: Calendar = Calendar.getInstance()
        val yNow: Int = cldr.get(Calendar.YEAR)
        val mNow: Int = cldr.get(Calendar.MONTH)
        val dNow: Int = cldr.get(Calendar.DAY_OF_MONTH)


        if(start){
            val picker = DatePickerDialog(this, {_,y,m,d -> handleSetDate(y,m,d,start)}, yNow,mNow,dNow)
            picker.show()
        }else{
            if(startDate.year == -1 && startDate.month == -1 && startDate.day ==-1){
                Toast.makeText(this, "Please enter start date first", Toast.LENGTH_SHORT).show()
            }else{
                val picker = DatePickerDialog(this, {_,y,m,d -> handleSetDate(y,m,d,start)}, startDate.year,startDate.month,startDate.day)
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
            Toast.makeText(this, "Please fill all the text fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (startDate.year == -1 || startDate.month == -1 || startDate.day == -1 || startDate.hour == -1 || startDate.minute == -1
            || endDate.year == -1 || endDate.month == -1 || endDate.day == -1 || endDate.hour == -1 || endDate.minute == -1
        ) {
            Toast.makeText(this, "Please fill all the time and date fields", Toast.LENGTH_SHORT).show()
            return
        }

        var checked : Boolean = false

        for(i in daysArray.indices){
            if(daysArray[i]){
                checked = true
            }
        }

        if(!checked){
            Toast.makeText(this, "Please check at least one day", Toast.LENGTH_SHORT).show()
            return
        }

        // insertar curso

        Toast.makeText(this, "Añadiendo curso", Toast.LENGTH_SHORT).show()
        finish()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cancelButton.setOnClickListener {
            finish()
        }
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