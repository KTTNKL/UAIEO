package com.khtn.uaieo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemMenu
import com.khtn.uaieo.model.itemQuiz

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val nextBtn: Button =findViewById(R.id.buttonNext)
        val option1Btn: Button = findViewById(R.id.buttonAnswer1)
        val option2Btn: Button = findViewById(R.id.buttonAnswer2)
        val option3Btn: Button = findViewById(R.id.buttonAnswer3)
        val option4Btn: Button = findViewById(R.id.buttonAnswer4)

        var quizArray = ArrayList<itemQuiz>()

        val ref= FirebaseDatabase.getInstance().getReference("quiz")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (quiz in snapshot.children){
                    val modelQuiz=quiz.getValue(itemQuiz::class.java)
                    quizArray.add(modelQuiz!!)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

//        quizArray[0].title?.let { Log.i("hdlog", it) }



        nextBtn.setOnClickListener{
            val intent: Intent = Intent (this, QuizResultActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun setData(){

    }
}