package com.khtn.uaieo.activity.Home

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemQuiz
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val nextBtn: Button =findViewById(R.id.buttonNext)
        val option1Btn: Button = findViewById(R.id.buttonAnswer1)
        val option2Btn: Button = findViewById(R.id.buttonAnswer2)
        val option3Btn: Button = findViewById(R.id.buttonAnswer3)
        val option4Btn: Button = findViewById(R.id.buttonAnswer4)
        val question:TextView = findViewById(R.id.QuizTitleTextview)


        var point = 0
        var questionNumber =1

        var questionIndex = ArrayList<Int>()
        var quizArray = ArrayList<itemQuiz>()

        val ref= FirebaseDatabase.getInstance().getReference("quiz")
        val dialog = ProgressDialog.show(
            this@QuizActivity, "",
            "Loading. Please wait...", true
        )

        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (quiz in snapshot.children){
                    val modelQuiz=quiz.getValue(itemQuiz::class.java)
                    quizArray.add(modelQuiz!!)
                }
                dialog.dismiss()
                //
                questionIndex = generateQuestion(quizArray.size)
                option1Btn.setText(quizArray[questionIndex[questionNumber-1]].option1)
                option2Btn.setText(quizArray[questionIndex[questionNumber-1]].option2)
                option3Btn.setText(quizArray[questionIndex[questionNumber-1]].option3)
                option4Btn.setText(quizArray[questionIndex[questionNumber-1]].option4)
                question.setText(quizArray[questionIndex[questionNumber-1]].title)


            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        var isClicked: Boolean = false

        option1Btn.setOnClickListener{
            if(quizArray[questionIndex[questionNumber-1]].option1?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                    correctAnswer(it1,
                        it2
                    )
                } } == true){
                point ++
                option1Btn.setBackgroundResource(R.drawable.bg_correct)
            }
            else{
                option1Btn.setBackgroundResource(R.drawable.bg_wrong)
                if(quizArray[questionIndex[questionNumber-1]].option2?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option2Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option3?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option3Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option4?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option4Btn.setBackgroundResource(R.drawable.bg_correct)
                }
            }
//            option1Btn.isEnabled = false
            option1Btn.isClickable = false
//            option2Btn.isEnabled = false
            option2Btn.isClickable = false
//            option3Btn.isEnabled = false
            option3Btn.isClickable = false
//            option4Btn.isEnabled = false
            option4Btn.isClickable = false
            isClicked = true
            questionNumber ++
        }

        option2Btn.setOnClickListener{
            if(quizArray[questionIndex[questionNumber-1]].option2?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                    correctAnswer(it1,
                        it2
                    )
                } } == true){
                point ++
                option2Btn.setBackgroundResource(R.drawable.bg_correct)
            }
            else{
                option2Btn.setBackgroundResource(R.drawable.bg_wrong)
                if(quizArray[questionIndex[questionNumber-1]].option1?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option1Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option3?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option3Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option4?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option4Btn.setBackgroundResource(R.drawable.bg_correct)
                }
            }
//            option1Btn.isEnabled = false
            option1Btn.isClickable = false
//            option2Btn.isEnabled = false
            option2Btn.isClickable = false
//            option3Btn.isEnabled = false
            option3Btn.isClickable = false
//            option4Btn.isEnabled = false
            option4Btn.isClickable = false
            isClicked = true
            questionNumber ++
        }

        option3Btn.setOnClickListener{
            if(quizArray[questionIndex[questionNumber-1]].option3?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                    correctAnswer(it1,
                        it2
                    )
                } } == true){
                point ++
                option3Btn.setBackgroundResource(R.drawable.bg_correct)
            }
            else{
                option3Btn.setBackgroundResource(R.drawable.bg_wrong)
                if(quizArray[questionIndex[questionNumber-1]].option2?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option2Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option1?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option1Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option4?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option4Btn.setBackgroundResource(R.drawable.bg_correct)
                }
            }
//            option1Btn.isEnabled = false
            option1Btn.isClickable = false
//            option2Btn.isEnabled = false
            option2Btn.isClickable = false
//            option3Btn.isEnabled = false
            option3Btn.isClickable = false
//            option4Btn.isEnabled = false
            option4Btn.isClickable = false
            isClicked = true
            questionNumber ++
        }

        option4Btn.setOnClickListener{
            if(quizArray[questionIndex[questionNumber-1]].option4?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                    correctAnswer(it1,
                        it2
                    )
                } } == true){
                point ++
                option4Btn.setBackgroundResource(R.drawable.bg_correct)
            }
            else{
                option4Btn.setBackgroundResource(R.drawable.bg_wrong)
                if(quizArray[questionIndex[questionNumber-1]].option2?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option2Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option3?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option3Btn.setBackgroundResource(R.drawable.bg_correct)
                }
                if(quizArray[questionIndex[questionNumber-1]].option1?.let { it1 -> quizArray[questionIndex[questionNumber-1]].answer?.let { it2 ->
                        correctAnswer(it1,
                            it2
                        )
                    } } == true){
                    option1Btn.setBackgroundResource(R.drawable.bg_correct)
                }
            }
//            option1Btn.isEnabled = false
            option1Btn.isClickable = false
//            option2Btn.isEnabled = false
            option2Btn.isClickable = false
//            option3Btn.isEnabled = false
            option3Btn.isClickable = false
//            option4Btn.isEnabled = false
            option4Btn.isClickable = false
            isClicked = true
            questionNumber ++
        }



        nextBtn.setOnClickListener{
            if(questionNumber == 5 && isClicked == false){
                questionNumber ++
            }

            if(questionNumber == 6) {
                val intent: Intent = Intent(this, QuizResultActivity::class.java)
                intent.putExtra("Point", point)
                startActivity(intent)
                finish()
            }
            else{
                if(isClicked == false){
                    questionNumber ++
                }
                isClicked = false
                questionTextView.setText("CÂU " + questionNumber.toString())
                option1Btn.setText(quizArray[questionIndex[questionNumber-1]].option1)
                option2Btn.setText(quizArray[questionIndex[questionNumber-1]].option2)
                option3Btn.setText(quizArray[questionIndex[questionNumber-1]].option3)
                option4Btn.setText(quizArray[questionIndex[questionNumber-1]].option4)
                question.setText(quizArray[questionIndex[questionNumber-1]].title)

                option1Btn.isClickable = true
                option2Btn.isClickable = true
                option3Btn.isClickable = true
                option4Btn.isClickable = true
                option1Btn.setBackgroundResource(R.drawable.bg_quiz_question)
                option2Btn.setBackgroundResource(R.drawable.bg_quiz_question)
                option3Btn.setBackgroundResource(R.drawable.bg_quiz_question)
                option4Btn.setBackgroundResource(R.drawable.bg_quiz_question)

            }
        }



    }

    fun generateQuestion(size: Int): ArrayList<Int>{
        var result = ArrayList<Int>()
        while (result.size < 5)
        {
            val rnds = (0..size-1).random()
            var exists = false
            for(j in 0..result.size-1){
                if(rnds == result[j] ) {
                    exists = true
                    break
                }
            }
            if(exists == false)
            {
                result.add(rnds)
            }
        }
        return result
    }

    fun correctAnswer(Option: String, Answer: String): Boolean{
        return Option.compareTo(Answer)==0
    }
}