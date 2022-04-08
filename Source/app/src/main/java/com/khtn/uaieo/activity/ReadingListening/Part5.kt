package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part1.*
import kotlinx.android.synthetic.main.activity_part3.*
import kotlinx.android.synthetic.main.activity_part5.*

class Part5 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    var correctAnswers = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part5)
        id= intent.getStringExtra("id").toString()
        loadDataPart5()
        clickNext()
    }

    private fun clickNext() {
        nextPart5Btn.setOnClickListener {
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                    Toast.makeText(this, "Part 5: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart5Btn.setText("XEM ĐIỂM")
                }
                else
                {
                    setData(num)
                }
            }
        }
    }

    private fun loadDataPart5() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part5")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val question = item.getValue(itemPartRL::class.java)
                    if (question != null) {
                        arr.add(question)
                    }
                }
                setData(0)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setData(num: Int) {
        titlePart5TV.text = "Câu ${arr[num].number}:"+arr[num].title

        textViewA_part5.text = arr[num].option1
        textViewB_part5.text = arr[num].option2
        textViewC_part5.text = arr[num].option3
        textViewD_part5.text = arr[num].option4

        buttonA_part5.isClickable = true
        buttonB_part5.isClickable = true
        buttonC_part5.isClickable = true
        buttonD_part5.isClickable = true

        buttonA_part5.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonB_part5.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonC_part5.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonD_part5.setBackgroundResource(R.drawable.bg_quiz_question)

        buttonA_part5.setOnClickListener {
            if(arr[num].answer == "A")
            {
                buttonA_part5.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonA_part5.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "B")
                {
                    buttonB_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part5.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part5.isClickable = false
            buttonB_part5.isClickable = false
            buttonC_part5.isClickable = false
            buttonD_part5.isClickable = false
        }

        buttonB_part5.setOnClickListener {
            if(arr[num].answer == "B")
            {
                buttonB_part5.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonB_part5.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part5.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part5.isClickable = false
            buttonB_part5.isClickable = false
            buttonC_part5.isClickable = false
            buttonD_part5.isClickable = false
        }

        buttonC_part5.setOnClickListener {
            if(arr[num].answer == "C")
            {
                buttonC_part5.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonC_part5.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "B")
                {
                    buttonB_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part5.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part5.isClickable = false
            buttonB_part5.isClickable = false
            buttonC_part5.isClickable = false
            buttonD_part5.isClickable = false
        }

        buttonD_part5.setOnClickListener {
            if(arr[num].answer == "D")
            {
                buttonD_part5.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonD_part5.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "B")
                {
                    buttonB_part5.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part5.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part5.isClickable = false
            buttonB_part5.isClickable = false
            buttonC_part5.isClickable = false
            buttonD_part5.isClickable = false
        }

    }
}