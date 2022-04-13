package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
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
import kotlinx.android.synthetic.main.activity_part1.*
import kotlinx.android.synthetic.main.activity_part3.*

class Part3 : AppCompatActivity() {
    var id=""
    var num=0
    var currPoint:Long=0
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part3)
        id= intent.getStringExtra("id").toString()
        loadDataPart3()
        clickNext()
        clickSound()
        checkExist()
    }
    private fun checkExist() {
        var auth = FirebaseAuth.getInstance()
        var curUID= auth.uid;
        var exits = FirebaseDatabase.getInstance().getReference("analyst/${id}/${curUID}").child("part3")!!
        exits!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    currPoint= snapshot.value as Long
                }
                else{
                    val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${id}/${curUID}")
                    reference.child("id").setValue("${curUID}")
                    reference.child("part3").setValue(0)
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
            reference.child("part3").setValue(correctAnswers)
        }
    }

    private fun clickNext() {
        nextPart3Btn.setOnClickListener {
            if( num<arr.size){
                num+=3
                if(num==arr.size){
                    num=arr.size-3
                    Toast.makeText(this, "Part 3: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart3Btn.setText("XEM ĐIỂM")
                }
                else{
                    media.reset()
                    setData(num)
                }
            }
        }
    }

    fun clickSound(){
        soundPart3Btn.setOnClickListener {
            if( num<arr.size){
                if(!media.isPlaying){
                    media.setDataSource(arr[num].audio)
                    media.prepare()
                    media.start()
                    soundPart3Btn.setText("DỪNG")
                }else{
                    media.stop()
                    media.reset()
                    soundPart3Btn.setText("PHÁT")
                }
            }
        }
    }

    fun setData(num: Int ){
        if (num < arr.size) {
            cau1part3TV.text = "Câu ${arr[num].number}:"+arr[num].title
            cau2part3TV.text = "Câu ${arr[num+1].number}:"+arr[num + 1].title
            cau3part3TV.text = "Câu ${arr[num+2].number}:"+arr[num + 2].title

            part3_ques1_A_TV.text = arr[num].option1
            part3_ques1_B_TV.text = arr[num].option2
            part3_ques1_C_TV.text = arr[num].option3
            part3_ques1_D_TV.text = arr[num].option4

            part3_ques2_A_TV.text = arr[num + 1].option1
            part3_ques2_B_TV.text = arr[num + 1].option2
            part3_ques2_C_TV.text = arr[num + 1].option3
            part3_ques2_D_TV.text = arr[num + 1].option4

            part3_ques3_A_TV.text = arr[num + 2].option1
            part3_ques3_B_TV.text = arr[num + 2].option2
            part3_ques3_C_TV.text = arr[num + 2].option3
            part3_ques3_D_TV.text = arr[num + 2].option4

            buttonA_part3_ques1.isClickable = true
            buttonB_part3_ques1.isClickable = true
            buttonC_part3_ques1.isClickable = true
            buttonD_part3_ques1.isClickable = true

            buttonA_part3_ques2.isClickable = true
            buttonB_part3_ques2.isClickable = true
            buttonC_part3_ques2.isClickable = true
            buttonD_part3_ques2.isClickable = true

            buttonA_part3_ques3.isClickable = true
            buttonB_part3_ques3.isClickable = true
            buttonC_part3_ques3.isClickable = true
            buttonD_part3_ques3.isClickable = true

            buttonA_part3_ques1.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonB_part3_ques1.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonC_part3_ques1.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonD_part3_ques1.setBackgroundResource(R.drawable.bg_quiz_question)

            buttonA_part3_ques2.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonB_part3_ques2.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonC_part3_ques2.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonD_part3_ques2.setBackgroundResource(R.drawable.bg_quiz_question)

            buttonA_part3_ques3.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonB_part3_ques3.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonC_part3_ques3.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonD_part3_ques3.setBackgroundResource(R.drawable.bg_quiz_question)


            // Cau hoi 1
            buttonA_part3_ques1.setOnClickListener {
                if(arr[num].answer == "A")
                {
                    buttonA_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonA_part3_ques1.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "B")
                    {
                        buttonB_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "D")
                    {
                        buttonD_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques1.isClickable = false
                buttonB_part3_ques1.isClickable = false
                buttonC_part3_ques1.isClickable = false
                buttonD_part3_ques1.isClickable = false
                updateScore()
            }

            buttonB_part3_ques1.setOnClickListener {
                if(arr[num].answer == "B")
                {
                    buttonB_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonB_part3_ques1.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "D")
                    {
                        buttonD_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques1.isClickable = false
                buttonB_part3_ques1.isClickable = false
                buttonC_part3_ques1.isClickable = false
                buttonD_part3_ques1.isClickable = false
                updateScore()
            }

            buttonC_part3_ques1.setOnClickListener {
                if(arr[num].answer == "C")
                {
                    buttonC_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonC_part3_ques1.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "B")
                    {
                        buttonB_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "D")
                    {
                        buttonD_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques1.isClickable = false
                buttonB_part3_ques1.isClickable = false
                buttonC_part3_ques1.isClickable = false
                buttonD_part3_ques1.isClickable = false
                updateScore()
            }

            buttonD_part3_ques1.setOnClickListener {
                if(arr[num].answer == "D")
                {
                    buttonD_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonD_part3_ques1.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "B")
                    {
                        buttonB_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC_part3_ques1.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques1.isClickable = false
                buttonB_part3_ques1.isClickable = false
                buttonC_part3_ques1.isClickable = false
                buttonD_part3_ques1.isClickable = false
                updateScore()
            }

            // Cau hoi 2
            buttonA_part3_ques2.setOnClickListener {
                if(arr[num + 1].answer == "A")
                {
                    buttonA_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonA_part3_ques2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 1].answer == "B")
                    {
                        buttonB_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "C")
                    {
                        buttonC_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "D")
                    {
                        buttonD_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques2.isClickable = false
                buttonB_part3_ques2.isClickable = false
                buttonC_part3_ques2.isClickable = false
                buttonD_part3_ques2.isClickable = false
                updateScore()
            }

            buttonB_part3_ques2.setOnClickListener {
                if(arr[num + 1].answer == "B")
                {
                    buttonB_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonB_part3_ques2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 1].answer == "A")
                    {
                        buttonA_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "C")
                    {
                        buttonC_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "D")
                    {
                        buttonD_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques2.isClickable = false
                buttonB_part3_ques2.isClickable = false
                buttonC_part3_ques2.isClickable = false
                buttonD_part3_ques2.isClickable = false
                updateScore()
            }

            buttonC_part3_ques2.setOnClickListener {
                if(arr[num + 1].answer == "C")
                {
                    buttonC_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonC_part3_ques2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 1].answer == "A")
                    {
                        buttonA_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "B")
                    {
                        buttonB_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "D")
                    {
                        buttonD_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques2.isClickable = false
                buttonB_part3_ques2.isClickable = false
                buttonC_part3_ques2.isClickable = false
                buttonD_part3_ques2.isClickable = false
                updateScore()
            }

            buttonD_part3_ques2.setOnClickListener {
                if(arr[num + 1].answer == "D")
                {
                    buttonD_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonD_part3_ques2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 1].answer == "A")
                    {
                        buttonA_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "B")
                    {
                        buttonB_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 1].answer == "C")
                    {
                        buttonC_part3_ques2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques2.isClickable = false
                buttonB_part3_ques2.isClickable = false
                buttonC_part3_ques2.isClickable = false
                buttonD_part3_ques2.isClickable = false
                updateScore()
            }

            // Cau hoi 3
            buttonA_part3_ques3.setOnClickListener {
                if(arr[num + 2].answer == "A")
                {
                    buttonA_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonA_part3_ques3.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 2].answer == "B")
                    {
                        buttonB_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "C")
                    {
                        buttonC_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "D")
                    {
                        buttonD_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques3.isClickable = false
                buttonB_part3_ques3.isClickable = false
                buttonC_part3_ques3.isClickable = false
                buttonD_part3_ques3.isClickable = false
                updateScore()
            }

            buttonB_part3_ques3.setOnClickListener {
                if(arr[num + 2].answer == "B")
                {
                    buttonB_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonB_part3_ques3.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 2].answer == "A")
                    {
                        buttonA_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "C")
                    {
                        buttonC_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "D")
                    {
                        buttonD_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques3.isClickable = false
                buttonB_part3_ques3.isClickable = false
                buttonC_part3_ques3.isClickable = false
                buttonD_part3_ques3.isClickable = false
                updateScore()
            }

            buttonC_part3_ques3.setOnClickListener {
                if(arr[num + 2].answer == "C")
                {
                    buttonC_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonC_part3_ques3.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 2].answer == "A")
                    {
                        buttonA_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "B")
                    {
                        buttonB_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "D")
                    {
                        buttonD_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques3.isClickable = false
                buttonB_part3_ques3.isClickable = false
                buttonC_part3_ques3.isClickable = false
                buttonD_part3_ques3.isClickable = false
                updateScore()
            }

            buttonD_part3_ques3.setOnClickListener {
                if(arr[num + 2].answer == "D")
                {
                    buttonD_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonD_part3_ques3.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num + 2].answer == "A")
                    {
                        buttonA_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "B")
                    {
                        buttonB_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num + 2].answer == "C")
                    {
                        buttonC_part3_ques3.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part3_ques3.isClickable = false
                buttonB_part3_ques3.isClickable = false
                buttonC_part3_ques3.isClickable = false
                buttonD_part3_ques3.isClickable = false
                updateScore()
            }

            try {
                Glide.with(this).load(arr[num + 2].image).into(imagePart3)
            } finally {

            }
        }
    }
    private fun loadDataPart3() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part3")
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

    override fun onBackPressed() {
        super.onBackPressed()
        media.stop()
        media.release()
        finish()
    }
}