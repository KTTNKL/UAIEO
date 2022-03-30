package com.khtn.uaieo.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.WSExamAdapter
import com.khtn.uaieo.model.ExamID
import com.khtn.uaieo.model.partSW
import java.util.*
import kotlin.collections.ArrayList

class SpeakingQuestionListActivity : AppCompatActivity() {

    lateinit var readingArrayList: ArrayList<partSW>
    lateinit var dialog: ProgressDialog
    lateinit var newRecyclerview: RecyclerView
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking_question_list)

        val intent = intent
        id = intent.getStringExtra("ExamIndex").toString()

        Log.d("speaking",id)

        readingArrayList = ArrayList<partSW>()

        newRecyclerview = findViewById(R.id.speakingQuestionRecyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)


        dialog = ProgressDialog.show(
            this, "",
            "Loading Reading. Please wait...", true
        )

        LoadData()
    }

    fun LoadData(){

        //Xoa list trc khi them vao moi lan vao app
        var path = "WSquestions/speaking/" + id

        val ref= FirebaseDatabase.getInstance().getReference(path)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                readingArrayList.clear()
                for (temp in snapshot.children){
                    val question = temp.getValue(partSW::class.java)
                    readingArrayList.add(question!!)
                }

                for(i in 0..(readingArrayList.size - 2)){
                    for(j in (i+1)..(readingArrayList.size-1)){
                        if(readingArrayList.get(i).number!! > readingArrayList.get(j).number!!){
                            Collections.swap(readingArrayList, i, j)
                        }
                    }
                }
                var TotalQuestion = ArrayList<ExamID>()
                for(i in 1..11){
                    TotalQuestion.add(ExamID("CÂU " + i.toString()))
                }
                dialog.dismiss()
                var adapter = WSExamAdapter(TotalQuestion)
                newRecyclerview.adapter = adapter
                adapter.setOnItemClickListener(object: WSExamAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        swapScreen(position)
                    }
                })
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun swapScreen(position:Int){
        val intent = Intent(this, SpeakingQuestionDetailActicity::class.java)
        intent.putExtra("QuestionData", readingArrayList.get(position))
        startActivityForResult(intent, 1111)
    }
}