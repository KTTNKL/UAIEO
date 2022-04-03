package com.khtn.uaieo.activity.ReadingListening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPart1
import com.khtn.uaieo.model.itemPart3
import kotlinx.android.synthetic.main.activity_part2.*

class Part3 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPart3>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part3)
        id= intent.getStringExtra("id").toString()
        loadDataPart3()

    }

    private fun loadDataPart3() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part3")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    for( child in item.children){
                        val question = child.getValue(itemPart3::class.java)
                        if (question != null) {
                            arr.add(question)
                        }
                    }
                }

//                for(i in arr){
//                    Log.d("MyScreen",i.title.toString())
//                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })    }
}