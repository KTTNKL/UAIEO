package com.khtn.uaieo.activity.ReadingListening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.CategoryAdapter
import com.khtn.uaieo.adapter.RLPartAdapter
import com.khtn.uaieo.model.ExamID

class ChooseCategoryRLActivity : AppCompatActivity() {
    var categoryList= ArrayList<String>()
    lateinit var adapter: CategoryAdapter
    lateinit var customListView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_category_rlactivity)
        setupLayout()
    }

    private fun setupLayout() {
        categoryList.add("Làm theo đề")
        categoryList.add("Làm theo Part")
        categoryList.add("Làm random đề")

        adapter= CategoryAdapter(categoryList)
        customListView = findViewById<RecyclerView>(R.id.categoryRLRV) as RecyclerView
        customListView.layoutManager = LinearLayoutManager(this)

        customListView!!.adapter = adapter
        adapter.setOnItemClickListener(object: CategoryAdapter.onItemClickListener {
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                when(position){
                    0->{
                        intent= Intent(this@ChooseCategoryRLActivity, ListRLExamActivity::class.java)
                    }
                    1->{
                        intent= Intent(this@ChooseCategoryRLActivity, PartRLExamActivity::class.java)
                        intent.putExtra("choosePartOnly",true);
                    }
                    2->{
                        intent= Intent(this@ChooseCategoryRLActivity, PartRLExamActivity::class.java)
                        intent.putExtra("randomQuestion",true);
                    }
                }
                startActivity(intent)

            }
        })
    }
}