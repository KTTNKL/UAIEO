package com.khtn.uaieo.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.Auth.LoginActivity
import kotlinx.android.synthetic.main.change_password_dialog.view.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    lateinit var user: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        val userReference= databaseReference?.child(user?.uid!!)?.child("username")?.addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //infor.text=snapshot.getValue().toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

    }
    fun changePassword(){
        changePasswordBT.setOnClickListener{
            val dialog=LayoutInflater.from(context).inflate(R.layout.change_password_dialog,null)
            val builder= context?.let { it1 -> AlertDialog.Builder(it1).setView(dialog).setTitle("Change Password") }
            val myDialog= builder?.show()
            dialog.changePassBT.setOnClickListener {
                val pass=dialog.changePassET.text
                if (myDialog != null) {
                    myDialog.dismiss()
                }

                user!!.updatePassword(pass.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context,"Change Password successfully",
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
            dialog.cancelBT.setOnClickListener {
                if (myDialog != null) {
                    myDialog.dismiss()
                }
            }

        }
    }
    fun logout(){
        logoutBT.setOnClickListener {
            auth.signOut()
            val intent= Intent(context, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}