package com.khtn.uaieo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.change_password_dialog.view.*


class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init ()
        loadProfile()
        changePassword()
        logout()
    }
    fun init (){
        auth = FirebaseAuth.getInstance()
        databaseReference= FirebaseDatabase.getInstance().reference!!.child("profile")
        user= auth.currentUser!!
    }
    fun loadProfile(){
        val userReference= databaseReference?.child(user?.uid!!)?.child("Email")?.addListenerForSingleValueEvent(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    infor.text=snapshot.getValue().toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
    fun changePassword(){
        changePassBT.setOnClickListener{
            val dialog=LayoutInflater.from(this).inflate(R.layout.change_password_dialog,null)
            val builder= AlertDialog.Builder(this).setView(dialog).setTitle("Change Password")
            val myDialog=builder.show()
            dialog.changePassBT.setOnClickListener {
                val pass=dialog.changePassET.text
                myDialog.dismiss()

                user!!.updatePassword(pass.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this@ProfileActivity,"Change Password successfully",Toast.LENGTH_LONG).show()
                        }
                    }
            }
            dialog.cancelBT.setOnClickListener {
                myDialog.dismiss()
            }

        }
    }
    fun logout(){
        logoutBT.setOnClickListener {
            auth.signOut()
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}