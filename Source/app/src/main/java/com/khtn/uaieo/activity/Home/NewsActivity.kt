package com.khtn.uaieo.activity.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.NewsAdapter
import com.khtn.uaieo.model.NewsModel

class NewsActivity : AppCompatActivity() {
    var newsRV: RecyclerView? = null
    lateinit var dbRef: DatabaseReference
    var newsTV: TextView? = null
    lateinit var newsAdapter: NewsAdapter

    var newsList = ArrayList<NewsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsRV = findViewById(R.id.newsRV)
        newsTV = findViewById(R.id.newsTV)

        newsAdapter = NewsAdapter(this, newsList)
        newsRV!!.adapter = newsAdapter
        newsRV!!.layoutManager = LinearLayoutManager(this)
        newsRV!!.setHasFixedSize(true)
        dbRef = FirebaseDatabase.getInstance().getReference("news")

        dbRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                for (news in snapshot.children){
                    val newsModel = news.getValue(NewsModel::class.java)
                    newsList.add(newsModel!!)
                }
                newsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        newsAdapter.onItemClick = {news ->
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra("urlString", news.url)

            startActivity(intent)
        }
    }
}