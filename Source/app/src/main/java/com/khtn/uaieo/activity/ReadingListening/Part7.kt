package com.khtn.uaieo.activity.ReadingListening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part3.*
import kotlinx.android.synthetic.main.activity_part7.*

class Part7 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part7)
        id= intent.getStringExtra("id").toString()
        loadDataPart7()
        clickNext()
    }

    private fun clickNext() {
        nextPart7Btn.setOnClickListener {
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                }
                setData(num)
            }
        }
    }

    private fun loadDataPart7() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part7")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    for( child in item.children){
                        val question = child.getValue(itemPartRL::class.java)
                        if (question != null) {
                            arr.add(question)
                        }
                    }
                }
                setData(0)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })    }

    private fun setData(i: Int) {
        titlePart7TV.text="Câu ${arr[num].number}:"+ arr[num].title
        try {
            Glide.with(this).load(arr[num].image).into(image1Part7)

        }finally {

        }
        try{
            Glide.with(this).load(arr[num].image2).into(image2Part7)

        }finally {

        }
        try{
            Glide.with(this).load(arr[num].image3).into(image3Part7)
        }
        finally {

        }
    }
}