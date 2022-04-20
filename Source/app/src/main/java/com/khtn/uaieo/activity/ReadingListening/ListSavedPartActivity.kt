package com.khtn.uaieo.activity.ReadingListening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.RLExamAdapter
import com.khtn.uaieo.adapter.RLPartAdapter
import com.khtn.uaieo.model.itemExamRL
import kotlinx.android.synthetic.main.activity_list_saved_part.*

class ListSavedPartActivity : AppCompatActivity() {
    var PartArray=ArrayList<Int>()
    lateinit var adapter: RLPartAdapter
    lateinit var recyclerPartview: RecyclerView
    lateinit var exam:itemExamRL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_saved_part)
        val intent=intent
        //exam= intent.getSerializableExtra("exam") as itemExamRL
        setupLayout()
    }
    private fun setupLayout() {
        for (i in 1..7){
            PartArray.add(i)
        }
        adapter= RLPartAdapter(PartArray)

        recyclerPartview = findViewById<RecyclerView>(R.id.recyclerPartview) as RecyclerView
        recyclerPartview!!.adapter = adapter
        adapter.setOnItemClickListener(object: RLPartAdapter.onItemClickListener {
            lateinit var intent: Intent
            var partnumber: Int = 0;

            override fun onItemClick(position: Int) {
                when(position){
                    0->{
                        partnumber = 1
                    }
                    1->{
                        partnumber = 2
                    }
                    2->{
                        partnumber = 3

                    }
                    3->{
                        partnumber = 4

                    }
                    4->{
                        partnumber = 5
                    }
                    5->{
                        partnumber = 6
                    }
                    6->{
                        partnumber = 7
                    }

                }
                intent= Intent(this@ListSavedPartActivity, ListQuestionOfEachSavedPartActivity::class.java)
                intent.putExtra("partnumber",partnumber)
                startActivity(intent)

            }
        })
        recyclerPartview.layoutManager = LinearLayoutManager(this)
    }
}