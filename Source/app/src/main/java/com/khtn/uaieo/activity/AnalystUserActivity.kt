package com.khtn.uaieo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemAnalystUser
import com.khtn.uaieo.model.itemExamRL

class AnalystUserActivity : AppCompatActivity() {
    var id=""
    var arrUser= ArrayList<itemAnalystUser>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyst_user)
        id= intent.getStringExtra("id").toString()
        loadData()
        setupLayout()
    }

    private fun setupLayout() {

    }

    private fun loadData() {
        val ref= FirebaseDatabase.getInstance().getReference("analyst/${id}")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                arrUser.clear()
                for (item in snapshot.children){
                    val modelUser = item.getValue(itemAnalystUser::class.java)
                    arrUser.add(modelUser!!)
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}