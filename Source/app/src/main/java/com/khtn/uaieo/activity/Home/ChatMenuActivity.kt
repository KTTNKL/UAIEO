package com.khtn.uaieo.activity.Home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.ReadingListening.ListQuestionOfEachSavedPartActivity
import com.khtn.uaieo.adapter.ChatMenuAdapter
import com.khtn.uaieo.adapter.MenuAdapter
import com.khtn.uaieo.model.itemMenu

class ChatMenuActivity : AppCompatActivity() {
    private lateinit var adapter: ChatMenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_menu)
        setupLayout()

    }

    private fun setupLayout() {
        val recyclerView= findViewById<RecyclerView>(R.id.chatMenuRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= GridLayoutManager(this,2)
        }
        var itemList:ArrayList<itemMenu>
        itemList= ArrayList()
        itemList.add(itemMenu(R.drawable.bg_chat1,getResources().getString(R.string.ask),getResources().getString(R.string.ask_detail)))
        itemList.add(itemMenu(R.drawable.bg_chat2,getResources().getString(R.string.talk),getResources().getString(R.string.talk_detail)))
        adapter= ChatMenuAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }
        adapter.setOnItemClickListener(object: ChatMenuAdapter.onItemClickListener{
            //lateinit var intent: Intent
            var topic=""
            override fun onItemClick(position: Int) {
                when(position){
                    0->topic="ask"
                    1->topic="talk"
                }
                Log.d("MyScreen",topic)
                var intent= Intent(this@ChatMenuActivity, ChatActivity::class.java)
                intent.putExtra("topic",topic)
                startActivity(intent)
            }
        })
    }
}