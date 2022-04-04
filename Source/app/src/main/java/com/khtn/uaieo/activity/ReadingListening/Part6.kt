package com.khtn.uaieo.activity.ReadingListening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_part3.*
import kotlinx.android.synthetic.main.activity_part6.*
import kotlinx.android.synthetic.main.activity_part6.titlePart6TV
import kotlinx.android.synthetic.main.activity_vietsub.*

class Part6 : AppCompatActivity() {
    var id=""
    var num=0;
    var arr=ArrayList<itemPartRL>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part6)
        id= intent.getStringExtra("id").toString()
        loadDataPart6()
        clickNext()
    }

    private fun clickNext() {
        nextPart6Btn.setOnClickListener {
            if( num<arr.size){
                num+=1
                if(num==arr.size){
                    num=arr.size-1
                }
                setData(num)
            }
        }
    }

    private fun loadDataPart6() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions").child(id).child("part6")
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

    private fun setData(i: Int) {
        titlePart6TV.setMovementMethod(ScrollingMovementMethod())
        numberPart6TV.text="Câu ${arr[num].number}"
        titlePart6TV.text=arr[num].title
    }
}