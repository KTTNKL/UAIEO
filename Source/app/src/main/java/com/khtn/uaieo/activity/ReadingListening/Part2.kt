package com.khtn.uaieo.activity.ReadingListening

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPart1
import kotlinx.android.synthetic.main.activity_part1.*
import kotlinx.android.synthetic.main.activity_part2.*

class Part2 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPart1>()
    var media= MediaPlayer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part2)
        id= intent.getStringExtra("id").toString()
        loadDataPart2()
        clickSound()
        clickNext()
    }

    private fun clickNext() {
        nextPart2Btn.setOnClickListener {
            num++
            if( num<arr.size){
                media.reset()
                setData(num)
            }
        }
    }

    fun clickSound(){
        soundPart2Btn.setOnClickListener {
            if( num<arr.size){
                media.setDataSource(arr[num].audio)
                media.prepare()
                media.start()
            }
        }
    }

    fun setData(num: Int ){
        questionPart2TV.text="Câu "+arr[num].number

    }

    private fun loadDataPart2() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part2")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val question = item.getValue(itemPart1::class.java)
                    if (question != null) {
                        arr.add(question)
                    }
                }
                questionPart2TV.text="Câu "+arr[0].number
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}