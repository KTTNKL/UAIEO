package com.khtn.uaieo.activity.Home

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.khtn.uaieo.R
import kotlinx.android.synthetic.main.activity_schedule_screen.*
import java.text.SimpleDateFormat
import java.util.*

class ScheduleScreen : AppCompatActivity() {
    var formatDate= SimpleDateFormat("dd/MM/yyyy", Locale.US)
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_screen)

        auth= FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        databaseReference= FirebaseDatabase.getInstance().reference.child("profile")
        databaseReference?.child(currentUser?.uid!!)?.child("ExamDate")?.addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    dateTv.text=snapshot.value.toString()
                    ShowDayLeft(snapshot.value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })


        selectDateTV.setOnClickListener {
            val getDate= Calendar.getInstance()
            val datepicker= DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog_MinWidth
            ,DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                val selectDate= Calendar.getInstance()
                    selectDate.set(Calendar.YEAR,i)
                    selectDate.set(Calendar.MONTH,i2)
                    selectDate.set(Calendar.DAY_OF_MONTH,i3)
                    val date= formatDate.format(selectDate.time)

                    val currentUserDB=databaseReference?.child(currentUser?.uid!!)
                    currentUserDB?.child("ExamDate")?.setValue(date.toString())
                    dateTv.text= date

                    ShowDayLeft(date.toString())

                },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))

            datepicker.show()
        }
    }
    // Show day left base on current date and date in input
    fun ShowDayLeft(date: String){
        var futureDate= formatDate.parse(date)
        var currentDate:Date =Date()
        var diff= futureDate.time-currentDate.time
        if(!currentDate.after(futureDate)){

            var day= (diff/(24*60*60*1000)).toInt()
            dayLeftTV.text="${day} ngày còn lại"
        }else if(currentDate.day==futureDate.day && currentDate.month==futureDate.month &&currentDate.year==futureDate.year){
            dayLeftTV.text="Bạn có ngày thi vào hôm nay"
        }else{
            dayLeftTV.text= "Chúc bạn may mắn với kết quả thi"

        }
    }
}