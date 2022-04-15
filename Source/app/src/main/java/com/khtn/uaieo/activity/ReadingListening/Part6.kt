package com.khtn.uaieo.activity.ReadingListening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part3.*
import kotlinx.android.synthetic.main.activity_part5.*
import kotlinx.android.synthetic.main.activity_part6.*
import kotlinx.android.synthetic.main.activity_part6.titlePart6TV
import kotlinx.android.synthetic.main.activity_vietsub.*

class Part6 : AppCompatActivity() {
    lateinit var exam: itemExamRL
    var num=0
    var currPoint:Long=0
    var arr=ArrayList<itemPartRL>()
    var correctAnswers = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part6)
        exam= intent.getSerializableExtra("exam") as itemExamRL
        loadDataPart6()
        clickNext()
        checkExist()
    }

    private fun clickNext() {
        nextPart6Btn.setOnClickListener {
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                    Toast.makeText(this, "Part 6: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart6Btn.setText("XEM ĐIỂM")
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
        var exits = FirebaseDatabase.getInstance().getReference("analyst/${exam.id}/${curUID}").child("part6")!!
        exits!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    currPoint= snapshot.value as Long
                }
                else{
                    val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
                    reference.child("id").setValue("${curUID}")
                    reference.child("part6").setValue(0)
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
            val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
            reference.child("id").setValue("${curUID}")
            reference.child("part6").setValue(correctAnswers)
        }
    }

    private fun loadDataPart6() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child("${exam.id}").child("part6")
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
        titlePart6TV.setMovementMethod(ScrollingMovementMethod())
        numberPart6TV.text="Câu ${arr[num].number}"
        titlePart6TV.text=arr[num].title

        textViewA_part6.text = arr[num].option1
        textViewB_part6.text = arr[num].option2
        textViewC_part6.text = arr[num].option3
        textViewD_part6.text = arr[num].option4

        buttonA_part6.isClickable = true
        buttonB_part6.isClickable = true
        buttonC_part6.isClickable = true
        buttonD_part6.isClickable = true

        buttonA_part6.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonB_part6.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonC_part6.setBackgroundResource(R.drawable.bg_quiz_question)
        buttonD_part6.setBackgroundResource(R.drawable.bg_quiz_question)

        buttonA_part6.setOnClickListener {
            if(arr[num].answer == "A")
            {
                buttonA_part6.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonA_part6.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "B")
                {
                    buttonB_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part6.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part6.isClickable = false
            buttonB_part6.isClickable = false
            buttonC_part6.isClickable = false
            buttonD_part6.isClickable = false
            updateScore()
        }

        buttonB_part6.setOnClickListener {
            if(arr[num].answer == "B")
            {
                buttonB_part6.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonB_part6.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part6.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part6.isClickable = false
            buttonB_part6.isClickable = false
            buttonC_part6.isClickable = false
            buttonD_part6.isClickable = false
            updateScore()
        }

        buttonC_part6.setOnClickListener {
            if(arr[num].answer == "C")
            {
                buttonC_part6.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonC_part6.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "B")
                {
                    buttonB_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "D")
                {
                    buttonD_part6.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part6.isClickable = false
            buttonB_part6.isClickable = false
            buttonC_part6.isClickable = false
            buttonD_part6.isClickable = false
            updateScore()
        }

        buttonD_part6.setOnClickListener {
            if(arr[num].answer == "D")
            {
                buttonD_part6.setBackgroundResource(R.drawable.bg_correct)
                correctAnswers++
            }
            else
            {
                buttonD_part6.setBackgroundResource(R.drawable.bg_wrong)
                if(arr[num].answer == "A")
                {
                    buttonA_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "B")
                {
                    buttonB_part6.setBackgroundResource(R.drawable.bg_correct)
                }
                if(arr[num].answer == "C")
                {
                    buttonC_part6.setBackgroundResource(R.drawable.bg_correct)
                }
            }
            buttonA_part6.isClickable = false
            buttonB_part6.isClickable = false
            buttonC_part6.isClickable = false
            buttonD_part6.isClickable = false
            updateScore()
        }

    }
}