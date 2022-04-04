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
import kotlinx.android.synthetic.main.activity_part3.*

class Part4 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    var media= MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part3)
        id= intent.getStringExtra("id").toString()
        loadDataPart4()
        clickNext()
        clickSound()
    }
    private fun clickNext() {
        nextPart3Btn.setOnClickListener {
            if( num<arr.size){
                num+=3
                if(num==arr.size){
                    num=arr.size-3
                }
                media.reset()
                setData(num)
            }
        }
    }

    fun clickSound(){
        soundPart3Btn.setOnClickListener {
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
        if (num < arr.size) {
            cau1part3TV.text = "Câu ${arr[num].number}:"+arr[num].title
            cau2part3TV.text = "Câu ${arr[num+1].number}:"+arr[num + 1].title
            cau3part3TV.text = "Câu ${arr[num+2].number}:"+arr[num + 2].title
            try {
                Glide.with(this).load(arr[num].image).into(imagePart3)
            } finally {

            }
        }
    }
    private fun loadDataPart4() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part4")
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
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        media.stop()
        media.release()
        finish()
    }
}