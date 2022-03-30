package com.khtn.uaieo.activity.Speaking

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.WSExamAdapter
import com.khtn.uaieo.model.ExamID

class SpeakingExamListActivity : AppCompatActivity() {
    lateinit var readingArrayList: ArrayList<ExamID>
    lateinit var dialog: ProgressDialog
    lateinit var newRecyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking_exam_list)

        readingArrayList = ArrayList<ExamID>()

        newRecyclerview = findViewById(R.id.speakingListRecyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)

        dialog = ProgressDialog.show(
            this, "",
            "Loading Reading. Please wait...", true
        )

        LoadData()
    }
    fun LoadData(){
        val ref= FirebaseDatabase.getInstance().getReference("WSquestions/speaking")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                readingArrayList.clear()

                var count = snapshot.childrenCount.toInt()
                for(i in 1..count){
                    readingArrayList.add(ExamID("speakingExam" + i.toString()))
                }
                dialog.dismiss()
                var adapter = WSExamAdapter(readingArrayList)
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
        val intent = Intent(this, SpeakingQuestionListActivity::class.java)
        intent.putExtra("ExamIndex", readingArrayList.get(position).id)
        startActivityForResult(intent, 1111)
    }
}