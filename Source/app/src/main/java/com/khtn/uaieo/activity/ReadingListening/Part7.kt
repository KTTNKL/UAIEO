package com.khtn.uaieo.activity.ReadingListening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part1.*
import kotlinx.android.synthetic.main.activity_part7.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Part7 : AppCompatActivity() {
    var isOneQuestion = false
    var auth = FirebaseAuth.getInstance()
    var curUID= auth.uid;
    lateinit var question: itemPartRL

    lateinit var exam: itemExamRL
    var num=0
    var currPoint:Long=0
    var arr=ArrayList<itemPartRL>()
    var correctAnswers = 0

    var choosePartOnly=false
    var randomQuestion=false

    lateinit var countDownTimer: CountDownTimer
    var totalAudioTime: Long = 1620000
    var totalTime: Long = 30000
    var isNext: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part7)
        val intent=intent
        isOneQuestion = intent.getBooleanExtra("isOneQuestion", false)
        choosePartOnly= intent.getBooleanExtra("choosePartOnly",false)
        randomQuestion =intent.getBooleanExtra("randomQuestion",false)
        if(isOneQuestion)
        {
            part7saveBtn.visibility = View.INVISIBLE
            nextPart7Btn.visibility = View.INVISIBLE
            question = intent.getSerializableExtra("question") as itemPartRL
            arr.add(question)
            setData(0)
        }
        else if(choosePartOnly){
            loadDataPart7PartOnly()
            saveClick("Part")
            clickNext()

        }else if(randomQuestion){
            loadDataPart7Random()
            saveClick("Random")
            clickNext()
        }
        else
        {
            exam= intent.getSerializableExtra("exam") as itemExamRL
            loadDataPart7()
            clickNext()
            checkExist()
            saveClick("")
        }

        runTimer()
    }

    private fun runTimer() {
        countDownTimer = object : CountDownTimer(totalTime.toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownTV7.setText("Th???i gian: " + millisUntilFinished / 1000)
                totalAudioTime = totalTime - millisUntilFinished
            }

            override fun onFinish() {
                totalAudioTime+=1000
                //countdownTV.setText("Time's Up!")
                isNext = true
                countDownTimer.cancel()

                if( num<arr.size){
                    num++
                    if(num==arr.size){
                        num=arr.size-1

                        if (isOneQuestion == false) {
                            Toast.makeText(this@Part7, "Part 7: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                            nextPart7Btn.setText("XEM ??I???M")
                        }
                    }
                    else{
                        countDownTimer.cancel()

                        if (isNext == true) {
                            countDownTimer.start()
                        }
                        setData(num)
                    }
                }
            }
        }.start()
    }

    private fun loadDataPart7Random() {
        val ref= FirebaseDatabase.getInstance().getReference("question").child("part7")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var temp= ArrayList<DataSnapshot>()
                for (item in snapshot.children){
                    temp.add(item)

                }
                Collections.shuffle(temp)
                for( item in temp){
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
        })        }

    private fun loadDataPart7PartOnly() {
        val ref= FirebaseDatabase.getInstance().getReference("question").child("part7")
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
        })    }

    private fun saveClick(type: String) {
        part7saveBtn.setOnClickListener {
            val questionid = "" + System.currentTimeMillis()
            val reference= FirebaseDatabase.getInstance().reference!!.child("profile/${curUID}/save/part7/${questionid}")


            var hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("idQuestion", questionid.toString())
            if(type=="Random"){
                hashMap.put("bookType", "Part 1")
                hashMap.put("id", "Random")
            }else if(type=="Part"){
                hashMap.put("bookType", "Part 1")
                hashMap.put("id","")
            }else{
                hashMap.put("bookType", exam.bookType!!)
                hashMap.put("id", exam.id!!)
            }

            hashMap.put("answer", arr[num].answer!!)
            hashMap.put("image", arr[num].image!!)
            hashMap.put("option1", arr[num].option1!!)
            hashMap.put("option2", arr[num].option2!!)
            hashMap.put("option3", arr[num].option3!!)
            hashMap.put("option4", arr[num].option4!!)
            hashMap.put("title", arr[num].title!!)
            hashMap.put("number", arr[num].number!!)
            reference.setValue(hashMap)
            Toast.makeText(this, resources.getString(R.string.save_qeustion_successfully), Toast.LENGTH_SHORT).show()
        }
    }

    private fun clickNext() {
        nextPart7Btn.setOnClickListener {
            countDownTimer.cancel()
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                    Toast.makeText(this, "Part 7: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart7Btn.setText("XEM ??I???M")
                }
                else
                {
                    runTimer()
                    setData(num)
                }

            }
        }
    }
    private fun checkExist() {
        var auth = FirebaseAuth.getInstance()
        var curUID= auth.uid;
        var exits = FirebaseDatabase.getInstance().getReference("analyst/${exam.id}/${curUID}").child("part7")!!
        exits!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    currPoint= snapshot.value as Long
                }
                else{
                    val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
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
        if(!isOneQuestion && !choosePartOnly &&!randomQuestion)
        {
            if(correctAnswers > currPoint)
            {
                var auth = FirebaseAuth.getInstance()
                var curUID= auth.uid;
                val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
                reference.child("id").setValue("${curUID}")
                reference.child("part7").setValue(correctAnswers)
            }
        }

    }

    private fun loadDataPart7() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child("${exam.id}").child("part7")
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
        titlePart7TV.text="C??u ${arr[num].number}:"+ arr[num].title

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

    override fun onBackPressed() {
        super.onBackPressed()
        countDownTimer.cancel()
        finish()
    }
}