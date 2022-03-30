package com.khtn.uaieo.activity.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.MainActivity
import kotlinx.android.synthetic.main.activity_quiz_result.*

class QuizResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)
        var intent = intent
        var point: Int = intent.getIntExtra("Point", 0)
        resultTextVIew.setText(point.toString()+"/5")

        OKbutton.setOnClickListener{
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
   }
}