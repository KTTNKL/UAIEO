package com.khtn.uaieo.activity.ReadingListening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part3.*
import kotlinx.android.synthetic.main.activity_part6.*
import kotlinx.android.synthetic.main.activity_part7.*

class Part7 : AppCompatActivity() {
    var id=""
    var num=0
    var currPoint:Long=0
    var arr=ArrayList<itemPartRL>()
    var correctAnswers = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part7)
        id= intent.getStringExtra("id").toString()
        loadDataPart7()
        clickNext()
        checkExist()
    }

    private fun clickNext() {
        nextPart7Btn.setOnClickListener {
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                    Toast.makeText(this, "Part 7: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart7Btn.setText("XEM ĐIỂM")
                }
                else
                {
                    setData(num)
                }

            }
        }
    }
    private fun checkExist() {
        var auth = FirebaseAuth.getInstance()
        var curUID= auth.uid;
        var exits = FirebaseDatabase.getInstance().getReference("analyst/${id}/${curUID}").child("part7")!!
        exits!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    currPoint= snapshot.value as Long
                }
                else{
                    val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${id}/${curUID}")
                    reference.child("id").setValue("${curUID}")
                    reference.child("part7").setValue(0)
                    reference.child("email").setValue(auth.currentUser?.email)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("No need")
            }
        })
    }

    private fun updateScore(){
        if(correctAnswers > currPoint)
        {
            var auth = FirebaseAuth.getInstance()
            var curUID= auth.uid;
            val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${id}/${curUID}")
            reference.child("id").setValue("${curUID}")
            reference.child("part7").setValue(correctAnswers)
        }
    }

    private fun loadDataPart7() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part7")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    for( child in item.children){
                        val question = child.getValue(itemPartRL::class.java)
                        if (question != null) {
                            arr.add(question)
                        }
                    }
                }
                setData(0)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setData(i: Int) {
        titlePart7TV.text="Câu ${arr[num].number}:"+ arr[num].title

        textViewA_part7.text = arr[num].option1
        textViewB_part7.text = arr[num].option2
        textViewC_part7.text = arr[num].option3
        textViewD_part7.text = arr[num].option4

        buttonA_part7.isClickable = true
        buttonB_part7.isClickable = true
        buttonC_part7.isClickable = true
        buttonD_part7.isClickable = true

        buttonA_part7.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonB_part7.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonC_part7.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonD_part7.setBackgroundResource(R.drawable.bg_quiz_question)

        buttonA_part7.setOnClickListener {
            if(arr[num].answer == "A")
            {
                buttonA_part7.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonA_part7.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "B")
                {
                    buttonB_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part7.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part7.isClickable = false
            buttonB_part7.isClickable = false
            buttonC_part7.isClickable = false
            buttonD_part7.isClickable = false
            updateScore()
        }

        buttonB_part7.setOnClickListener {
            if(arr[num].answer == "B")
            {
                buttonB_part7.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonB_part7.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part7.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part7.isClickable = false
            buttonB_part7.isClickable = false
            buttonC_part7.isClickable = false
            buttonD_part7.isClickable = false
            updateScore()
        }

        buttonC_part7.setOnClickListener {
            if(arr[num].answer == "C")
            {
                buttonC_part7.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonC_part7.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "B")
                {
                    buttonB_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part7.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part7.isClickable = false
            buttonB_part7.isClickable = false
            buttonC_part7.isClickable = false
            buttonD_part7.isClickable = false
            updateScore()
        }

        buttonD_part7.setOnClickListener {
            if(arr[num].answer == "D")
            {
                buttonD_part7.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonD_part7.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "B")
                {
                    buttonB_part7.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part7.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part7.isClickable = false
            buttonB_part7.isClickable = false
            buttonC_part7.isClickable = false
            buttonD_part7.isClickable = false
            updateScore()
        }

        try {
            Glide.with(this).load(arr[num].image).into(image1Part7)

        }finally {

        }
        try{
            Glide.with(this).load(arr[num].image2).into(image2Part7)

        }finally {

        }
        try{
            Glide.with(this).load(arr[num].image3).into(image3Part7)
        }
        finally {

        }
    }
}