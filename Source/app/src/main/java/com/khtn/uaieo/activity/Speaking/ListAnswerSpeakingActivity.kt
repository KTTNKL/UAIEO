package com.khtn.uaieo.activity.Speaking

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
import com.khtn.uaieo.model.WritingSpeakingExample
import kotlin.collections.ArrayList

class ListAnswerSpeakingActivity : AppCompatActivity() {
    lateinit var readingArrayList: ArrayList<WritingSpeakingExample>
    lateinit var dialog: ProgressDialog
    lateinit var newRecyclerview: RecyclerView
    lateinit var ID: String
    var path: String = ""
    var questionNumber: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_answer_speaking)

        ID = intent.getStringExtra("id").toString();
        questionNumber = intent.getStringExtra("number")?.toInt()!!;

        readingArrayList = ArrayList<WritingSpeakingExample>()

        newRecyclerview = findViewById(R.id.listExampleRecyclerView2)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)


        dialog = ProgressDialog.show(
            this, "",
            "Loading, Please wait...", true
        )

        LoadData()
}
    fun LoadData(){

        //Xoa list trc khi them vao moi lan vao app
        path = "WSquestions/speaking/" + ID + "/" +"question" + questionNumber.toString() + "/example";

        val ref= FirebaseDatabase.getInstance().getReference(path)
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                readingArrayList.clear()
                for (temp in snapshot.children){
                    val question = temp.getValue(WritingSpeakingExample::class.java)
                    readingArrayList.add(question!!)
                }

                var TotalQuestion = ArrayList<ExamID>()
                for(i in 0..(readingArrayList.size - 1)){
                    TotalQuestion.add(ExamID("Answer " + (i+1).toString()))
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
        val intent = Intent(this, DetailAnswerSpeakingActivity::class.java)
        intent.putExtra("path", path + "/" + readingArrayList.get(position).id)
        intent.putExtra("AnswerData", readingArrayList.get(position))
        startActivityForResult(intent, 1111)
    }
}