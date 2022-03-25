package com.khtn.uaieo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Vietsub

class VietsubActivity : AppCompatActivity() {
    lateinit var readingArrayList: ArrayList<Vietsub>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vietsub)
//        readingArrayList = arrayListOf<Vietsub>()
//        val ref= FirebaseDatabase.getInstance().getReference("vietsub")
//        ref.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                //Xoa list trc khi them vao moi lan vao app
//                //videoArrayList.clear()
//                for (vietsub in snapshot.children){
//                    val modelVideo = vietsub.getValue(Vietsub::class.java)
//                    readingArrayList.add(modelVideo!!)
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//
//        for(i in readingArrayList){
//            i.title?.let { Log.d("Test", it) }
//        }
    }
}