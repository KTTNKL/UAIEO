package com.khtn.uaieo.activity.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.MainActivity
import kotlinx.android.synthetic.main.activity_logout_acvitiy.*

class LogoutAcvitiy : AppCompatActivity() {
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout_acvitiy)
        checkIfAlreadyLogin()
        toLoginPage.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        toRegisterPage.setOnClickListener {
            val intent= Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    fun checkIfAlreadyLogin(){
        auth = FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        if (currentUser!=null){
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}