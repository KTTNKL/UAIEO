package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class Part1 : AppCompatActivity() {
    var isOneQuestion = false
    lateinit var exam:itemExamRL
    var num=0;
    var currPoint:Long=0
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    var correctAnswers = 0
    var auth = FirebaseAuth.getInstance()
    var curUID= auth.uid;
    lateinit var question: itemPartRL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part1)
        val intent=intent
        isOneQuestion = intent.getBooleanExtra("isOneQuestion", false)


        if(isOneQuestion)
        {
            // xem cau hoi da luu
            question = intent.getSerializableExtra("question") as itemPartRL
            arr.add(question)
            setData(0)
            clickSound()
            part1saveBtn.visibility = View.INVISIBLE
            nextPart1Btn.visibility = View.INVISIBLE
        }
        else
        {
             // chay binh thuong
            exam= intent.getSerializableExtra("exam") as itemExamRL
            loadDataPart1()
            clickSound()
            clickNext()
            checkExist()
            saveClick()
        }
    }

    private fun saveClick() {
        part1saveBtn.setOnClickListener {
            val questionid = "" + System.currentTimeMillis()
            val reference= FirebaseDatabase.getInstance().reference!!.child("profile/${curUID}/save/part1/${questionid}")


            var hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("idQuestion", questionid.toString())
            hashMap.put("bookType", exam.bookType!!)
            hashMap.put("id", exam.id!!)
            hashMap.put("answer", arr[num].answer!!)
            hashMap.put("audio", arr[num].audio!!)
            hashMap.put("image", arr[num].image!!)
            hashMap.put("number", arr[num].number!!)
            reference.setValue(hashMap)



        }
//        val reference= FirebaseDatabase.getInstance().reference!!.child("profile/${curUID}/save/part1/")
//        reference.child("part1").setValue(0)
//        reference.child("email").setValue(auth.currentUser?.email)

    }

    private fun checkExist() {
        var exits = FirebaseDatabase.getInstance().getReference("analyst/${exam.id}/${curUID}").child("part1")!!
        exits!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    currPoint= snapshot.value as Long
                }
                else{
                    val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
                    reference.child("id").setValue("${curUID}")
                    reference.child("part1").setValue(0)
                    reference.child("email").setValue(auth.currentUser?.email)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("No need")
            }
        })
    }

    private fun updateScore(){
        if(!isOneQuestion)
        {
            if(correctAnswers > currPoint)
            {
                var auth = FirebaseAuth.getInstance()
                var curUID= auth.uid;
                val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
                reference.child("id").setValue("${curUID}")
                reference.child("part1").setValue(correctAnswers)
            }
        }

    }


    private fun loadDataPart1() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child("${exam.id}").child("part1")
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
                updateScore()
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
                updateScore()
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
                updateScore()
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
                updateScore()
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