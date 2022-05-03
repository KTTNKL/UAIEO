package com.khtn.uaieo.activity.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.khtn.uaieo.R
import kotlinx.android.synthetic.main.activity_forget_password.*


class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        auth= FirebaseAuth.getInstance()
        forgettPassword()
    }

    private fun forgettPassword() {
        submitBT.setOnClickListener(){
            if(TextUtils.isEmpty(emailForgetET.text.toString())){
                emailForgetET.error = "Vui lòng nhập email!"
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(emailForgetET.text.toString())
                .addOnCompleteListener{ task ->
                    if ( task.isSuccessful ){
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "Vui lòng kiểm tra email!",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }else {
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            task.exception!!.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

        }    }
}