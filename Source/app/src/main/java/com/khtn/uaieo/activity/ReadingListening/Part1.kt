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
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part1.*

class Part1 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part1)
        val intent=intent
        id= intent.getStringExtra("id").toString()
        loadDataPart1()
        clickSound()
        clickNext()
    }




    private fun loadDataPart1() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part1")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val question = item.getValue(itemPartRL::class.java)
                    if (question != null) {
                        arr.add(question)
                    }
                }
                Glide.with(this@Part1).load(arr[0].image).into(part1IV)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }




    private fun clickNext() {
        nextPart1Btn.setOnClickListener {

            if( num<arr.size){
                num++
                if(num==arr.size){
                    num=arr.size-1
                }
                media.reset()
                setData(num)
            }
        }
    }



    fun clickSound(){
        audioPart1Btn.setOnClickListener {
            if( num<arr.size){
                if(!media.isPlaying){
                    media.setDataSource(arr[num].audio)
                    media.prepare()
                    media.start()
                }else{
                    media.stop()
                    media.reset()
                }
            }

        }
    }

    fun setData(num: Int ){
        if( num<arr.size) {
            Glide.with(this).load(arr[num].image).into(part1IV)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        media.stop()
        media.release()
        finish()
    }
}