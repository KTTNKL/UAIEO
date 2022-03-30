package com.khtn.uaieo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.khtn.uaieo.R
import com.khtn.uaieo.model.partSW
import kotlinx.android.synthetic.main.activity_speaking_question_detail_acticity.*

class SpeakingQuestionDetailActicity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking_question_detail_acticity)

        var question = intent.getSerializableExtra("QuestionData") as partSW

        Glide.with(this).load(question.image).into(speakingImage)
        questionNumberTV.text = "CÂU: " + question.number.toString()
        contentQuestionTV.text = question.content
        questionTV.text = question.question

    }
}