package com.khtn.uaieo.activity.ReadingListening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.RLExamAdapter
import com.khtn.uaieo.adapter.RLPartAdapter
import com.khtn.uaieo.adapter.SavedQuestionAdapter
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.itemPartRL
import kotlinx.android.synthetic.main.activity_list_question_of_each_saved_part.*

class ListQuestionOfEachSavedPartActivity : AppCompatActivity() {
    var ExamArray=ArrayList<itemPartRL>()
    lateinit var adapter: SavedQuestionAdapter
    lateinit var customListView: RecyclerView
    var partNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_question_of_each_saved_part)
        partNumber = intent.getIntExtra("partnumber", 1)

        loadData()
        setupLayout()
    }

    private fun loadData() {
        var user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        Log.d("hd", user.uid)
        val ref= FirebaseDatabase.getInstance().getReference("profile/${user.uid}/save/part${partNumber}")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                ExamArray.clear()
                for (item in snapshot.children){
                    val modelExam = item.getValue(itemPartRL::class.java)
                    Log.d("hd", modelExam!!.bookType.toString())
                    ExamArray.add(modelExam!!)
                }
                if(ExamArray.size == 0)
                {
                    announceTV.text = "NO QUESTION WAS SAVED IN THIS PART !!!!"
                }
                else
                {
                    announceTV.text = ""
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })    }

    private fun setupLayout() {
        adapter= SavedQuestionAdapter(ExamArray)
        customListView = findViewById<RecyclerView>(R.id.savedquestionRV) as RecyclerView
        customListView!!.adapter = adapter
        adapter.setOnItemClickListener(object: SavedQuestionAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                lateinit var intent: Intent

                when(partNumber)
                {
                    1-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part1::class.java)
                    2-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part2::class.java)
                    3-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part3::class.java)
                    4-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part4::class.java)
                    5-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part5::class.java)
                    6-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part6::class.java)
                    7-> intent= Intent(this@ListQuestionOfEachSavedPartActivity, Part7::class.java)
                }
                intent.putExtra("question",ExamArray[position])
                intent.putExtra("isOneQuestion", true)
                startActivity(intent)
            }
        })
        customListView.layoutManager = LinearLayoutManager(this)

    }
}