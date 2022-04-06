package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part1.*

class Part1 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part1)
        val intent=intent
        id= intent.getStringExtra("id").toString()
        loadDataPart1()
        clickSound()
        clickNext()


    }




    private fun loadDataPart1() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part1")
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




    private fun clickNext() {
        nextPart1Btn.setOnClickListener {

            if( num<arr.size){
                num++
                if(num==arr.size){
                    num=arr.size-1
                    Toast.makeText(this, "Part 1: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart1Btn.setText("XEM ĐIỂM")
                }
                else{
                    media.reset()
                    setData(num)
                }

            }
        }
    }



    fun clickSound(){
        audioPart1Btn.setOnClickListener {
            if( num<arr.size){
                if(!media.isPlaying){
                    media.setDataSource(arr[num].audio)
                    media.prepare()
                    media.start()
                    audioPart1Btn.setText("DỪNG")
                }else{
                    media.stop()
                    media.reset()
                    audioPart1Btn.setText("PHÁT")
                }
            }

        }
    }

    fun setData(num: Int ){
        if( num<arr.size) {
            questionNumberTextViewPart1.text = "Câu " + arr[num].number
            Glide.with(this).load(arr[num].image).into(part1IV)
            buttonA.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonB.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonC.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonD.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonA.isClickable = true
            buttonB.isClickable = true
            buttonC.isClickable = true
            buttonD.isClickable = true

            buttonA.setOnClickListener {
                if(arr[num].answer == "A")
                {
                    buttonA.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonA.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "B")
                    {
                        buttonB.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "D")
                    {
                        buttonD.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA.isClickable = false
                buttonB.isClickable = false
                buttonC.isClickable = false
                buttonD.isClickable = false
            }

            buttonB.setOnClickListener {
                if(arr[num].answer == "B")
                {
                    buttonB.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonB.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "D")
                    {
                        buttonD.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA.isClickable = false
                buttonB.isClickable = false
                buttonC.isClickable = false
                buttonD.isClickable = false
            }

            buttonC.setOnClickListener {
                if(arr[num].answer == "C")
                {
                    buttonC.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonC.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "B")
                    {
                        buttonB.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "D")
                    {
                        buttonD.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA.isClickable = false
                buttonB.isClickable = false
                buttonC.isClickable = false
                buttonD.isClickable = false
            }

            buttonD.setOnClickListener {
                if(arr[num].answer == "D")
                {
                    buttonD.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonD.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "B")
                    {
                        buttonB.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA.isClickable = false
                buttonB.isClickable = false
                buttonC.isClickable = false
                buttonD.isClickable = false
            }

        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        media.stop()
        media.release()
        finish()
    }
}