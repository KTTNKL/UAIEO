package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part1.*
import kotlinx.android.synthetic.main.activity_part2.*

class Part2 : AppCompatActivity() {
    var isOneQuestion = false
    var auth = FirebaseAuth.getInstance()
    var curUID= auth.uid;
    lateinit var question: itemPartRL


    lateinit var exam: itemExamRL
    var num=0
    var currPoint:Long=0
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    var correctAnswers = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2)
        val intent=intent
        isOneQuestion = intent.getBooleanExtra("isOneQuestion", false)

        if(isOneQuestion)
        {
            part2saveBtn.visibility = View.INVISIBLE
            nextPart2Btn.visibility = View.INVISIBLE
            question = intent.getSerializableExtra("question") as itemPartRL
            arr.add(question)
            setData(0)
            clickSound()
        }
        else
        {
            exam= intent.getSerializableExtra("exam") as itemExamRL
            loadDataPart2()
            clickSound()
            clickNext()
            checkExist()
            saveClick()
        }
    }
    private fun saveClick() {
        part2saveBtn.setOnClickListener {
            val questionid = "" + System.currentTimeMillis()
            val reference= FirebaseDatabase.getInstance().reference!!.child("profile/${curUID}/save/part2/${questionid}")


            var hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("idQuestion", questionid.toString())
            hashMap.put("bookType", exam.bookType!!)
            hashMap.put("id", exam.id!!)
            hashMap.put("answer", arr[num].answer!!)
            hashMap.put("audio", arr[num].audio!!)
            hashMap.put("number", arr[num].number!!)
            reference.setValue(hashMap)
        }
    }

    private fun checkExist() {
        var auth = FirebaseAuth.getInstance()
        var curUID= auth.uid;
        var exits = FirebaseDatabase.getInstance().getReference("analyst/${exam.id}/${curUID}").child("part2")!!
        exits!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    currPoint= snapshot.value as Long
                }
                else{
                    val reference= FirebaseDatabase.getInstance().reference!!.child("analyst/${exam.id}/${curUID}")
                    reference.child("id").setValue("${curUID}")
                    reference.child("part2").setValue(0)
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
                reference.child("part2").setValue(correctAnswers)
            }
        }

    }

    private fun clickNext() {
        nextPart2Btn.setOnClickListener {
            if( num<arr.size){
                num++
                if(num==arr.size){
                    num=arr.size-1
                    Toast.makeText(this, "Part 2: " + correctAnswers.toString() + "/" + arr.size, Toast.LENGTH_SHORT).show()
                    nextPart2Btn.setText("XEM ĐIỂM")
                }
                else{
                    media.reset()
                    setData(num)
                }

            }
        }
    }

    fun clickSound(){
        soundPart2Btn.setOnClickListener {

            if( num<arr.size){
                if(!media.isPlaying){
                    media.setDataSource(arr[num].audio)
                    media.prepare()
                    media.start()
                    soundPart2Btn.setText("DỪNG")
                }else{
                    media.stop()
                    media.reset()
                    soundPart2Btn.setText("PHÁT")
                }
            }
        }
    }

    fun setData(num: Int ){
        if( num<arr.size) {
            questionPart2TV.text = "Câu " + arr[num].number
            buttonA_part2.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonB_part2.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonC_part2.setBackgroundResource(R.drawable.bg_quiz_question)
            buttonA_part2.isClickable = true
            buttonB_part2.isClickable = true
            buttonC_part2.isClickable = true

            buttonA_part2.setOnClickListener {
                if(arr[num].answer == "A")
                {
                    buttonA_part2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonA_part2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "B")
                    {
                        buttonB_part2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC_part2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part2.isClickable = false
                buttonB_part2.isClickable = false
                buttonC_part2.isClickable = false
                updateScore()
            }
            buttonB_part2.setOnClickListener {
                if(arr[num].answer == "B")
                {
                    buttonB_part2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonB_part2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA_part2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "C")
                    {
                        buttonC_part2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part2.isClickable = false
                buttonB_part2.isClickable = false
                buttonC_part2.isClickable = false
                updateScore()
            }
            buttonC_part2.setOnClickListener {
                if(arr[num].answer == "C")
                {
                    buttonC_part2.setBackgroundResource(R.drawable.bg_correct)
                    correctAnswers++
                }
                else
                {
                    buttonC_part2.setBackgroundResource(R.drawable.bg_wrong)
                    if(arr[num].answer == "A")
                    {
                        buttonA_part2.setBackgroundResource(R.drawable.bg_correct)
                    }
                    if(arr[num].answer == "B")
                    {
                        buttonB_part2.setBackgroundResource(R.drawable.bg_correct)
                    }
                }
                buttonA_part2.isClickable = false
                buttonB_part2.isClickable = false
                buttonC_part2.isClickable = false
                updateScore()
            }
        }
    }

    private fun loadDataPart2() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child("${exam.id}").child("part2")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val question = item.getValue(itemPartRL::class.java)
                    if (question != null) {
                        arr.add(question)
                    }
                }
                questionPart2TV.text="Câu "+arr[0].number
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