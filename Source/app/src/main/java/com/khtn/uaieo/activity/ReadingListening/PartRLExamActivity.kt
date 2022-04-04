package com.khtn.uaieo.activity.ReadingListening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.Home.*
import com.khtn.uaieo.adapter.RLExamAdapter
import com.khtn.uaieo.adapter.RLPartAdapter
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.partSW

class PartRLExamActivity : AppCompatActivity() {
    var PartArray=ArrayList<Int>()
    lateinit var adapter: RLPartAdapter
    lateinit var customListView: RecyclerView
     var id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_part_rlexam)
        val intent=intent
        id= intent.getStringExtra("id").toString()
        setupLayout()

    }

    private fun setupLayout() {
        for (i in 1..7){
            PartArray.add(i)
        }
        adapter= RLPartAdapter(PartArray)
        customListView = findViewById<RecyclerView>(R.id.partRLRV) as RecyclerView
        customListView!!.adapter = adapter
        adapter.setOnItemClickListener(object: RLPartAdapter.onItemClickListener {
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                when(position){
                    0->{
                        intent= Intent(this@PartRLExamActivity, Part1::class.java)
                    }
                    1->{
                        intent= Intent(this@PartRLExamActivity, Part2::class.java)
                    }
                    2->{
                        intent= Intent(this@PartRLExamActivity, Part3::class.java)

                    }
                    3->{
                        intent= Intent(this@PartRLExamActivity, Part4::class.java)

                    }
                    4->{
                        intent= Intent(this@PartRLExamActivity, Part5::class.java)
                    }
                    5->{
                        intent= Intent(this@PartRLExamActivity, Part6::class.java)
                    }
                    6->{
                        intent= Intent(this@PartRLExamActivity, Part7::class.java)
                    }

                }
                intent.putExtra("id",id)
                startActivity(intent)

            }
        })
        customListView.layoutManager = LinearLayoutManager(this)
    }
}