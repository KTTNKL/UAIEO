package com.khtn.uaieo.activity.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.MainActivity

class LoginActivity : AppCompatActivity() {
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        registerTV.setOnClickListener {
//            val intent= Intent(this, RegisterActivity::class.java)
//            startActivity(intent)
//        }
        checkIfAlreadyLogin()
        loginBT.setOnClickListener {
            if (TextUtils.isEmpty(emailET.text.toString())){
                emailET.setError("Please enter email")
                return@setOnClickListener
            } else if (TextUtils.isEmpty(passwordET.text.toString())){
                passwordET.setError("Please enter password")
                return@setOnClickListener
            }else{
            login(emailET.text.toString(),passwordET.text.toString())
            }
        }
        forgetPassword()
    }
    fun checkIfAlreadyLogin(){

        val currentUser=auth.currentUser
        if (currentUser!=null){
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun forgetPassword() {
        forgetPassTV.setOnClickListener {
            val intent=Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
    }


    fun login(_email:String,_pass:String){

        var email=_email.trim { it<= ' ' }
        var pass=_pass.trim{ it<= ' '}
        auth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    //Neu thanh cong --> Chuyen sang man hinh profie
                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    //Ket thuc
                }else{
                    //Neu dang nhap that bai
                    Toast.makeText(this@LoginActivity,"Login failed, please try again!", Toast.LENGTH_LONG).show()
                }
            }
        }
}