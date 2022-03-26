package com.khtn.uaieo.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
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
//                    val date= formatDate.format(snapshot.value.toString())
//                    dateTv.text=date
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



                    //Lay instance cua user dang vao
                    val currentUserDB=databaseReference?.child(currentUser?.uid!!)
                    //Dua thong tin co ban luc resgister vao database instance
                    currentUserDB?.child("ExamDate")?.setValue(selectDate.time)
                    dateTv.text= date
                },getDate.get(Calendar.YEAR),getDate.get(Calendar.MONTH),getDate.get(Calendar.DAY_OF_MONTH))

            datepicker.show()
        }
    }
}