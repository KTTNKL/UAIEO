package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part1.*
import kotlinx.android.synthetic.main.activity_part3.*
import kotlinx.android.synthetic.main.activity_part5.*

class Part5 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part5)
        id= intent.getStringExtra("id").toString()
        loadDataPart5()
        clickNext()
    }

    private fun clickNext() {
        nextPart5Btn.setOnClickListener {
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                }
                setData(num)
            }
        }
    }

    private fun loadDataPart5() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part5")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val question = item.getValue(itemPartRL::class.java)
                    if (question != null) {
                        arr.add(question)
                    }
                }
                setData(0)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setData(num: Int) {
        titlePart5TV.text = "Câu ${arr[num].number}:"+arr[num].title
    }
}