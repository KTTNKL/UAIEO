package com.khtn.uaieo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.khtn.uaieo.R
import com.khtn.uaieo.model.NewsModel

class NewsActivity : AppCompatActivity() {
    var newsRV: RecyclerView? = null
    lateinit var dbRef: DatabaseReference
    var newsTV: TextView? = null

    private lateinit var newsList : ArrayList<NewsModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        newsRV = findViewById(R.id.newsRV)
        newsTV = findViewById(R.id.newsTV)

        dbRef = FirebaseDatabase.getInstance().getReference("news")
        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                for (news in snapshot.children){
                    val newsModel = news.getValue(NewsModel::class.java)
                    newsList.add(newsModel!!)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        //newsTV.setText(newsList.get(0).url)
    }
}