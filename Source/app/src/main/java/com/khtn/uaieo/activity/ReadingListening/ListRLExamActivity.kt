package com.khtn.uaieo.activity.ReadingListening

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
import com.khtn.uaieo.adapter.RLExamAdapter
import com.khtn.uaieo.model.itemExamRL

class ListRLExamActivity : AppCompatActivity() {
    var ExamArray=ArrayList<itemExamRL>()
    lateinit var adapter:RLExamAdapter
    lateinit var customListView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rlexam)
        loadData()
        setupLayout()
    }

    private fun loadData() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                ExamArray.clear()
                for (item in snapshot.children){
                    val modelExam = item.getValue(itemExamRL::class.java)
                    ExamArray.add(modelExam!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })    }

    private fun setupLayout() {
        adapter= RLExamAdapter(ExamArray)
        customListView = findViewById<RecyclerView>(R.id.listRLexamRV) as RecyclerView
        customListView!!.adapter = adapter
        adapter.setOnItemClickListener(object: RLExamAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent=Intent(this@ListRLExamActivity, PartRLExamActivity::class.java)
                intent.putExtra("exam",ExamArray[position])
                startActivity(intent)
            }
        })
        customListView.layoutManager = LinearLayoutManager(this)

    }
}