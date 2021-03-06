package com.khtn.uaieo.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.khtn.uaieo.R
import com.khtn.uaieo.activity.Home.ChatActivity
import com.khtn.uaieo.activity.Home.*
import com.khtn.uaieo.activity.NotificationDetailActivity
import com.khtn.uaieo.adapter.MenuAdapter
import com.khtn.uaieo.model.itemMenu

class HomeFragment : Fragment() {

    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_home, container, false)
        initRecyclerView(view)
        val chatFAB: View? = view?.findViewById(R.id.chatFAB)

        chatFAB?.setOnClickListener {
            val intent = Intent(context, ChatMenuActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.homeRecycleView)
        if (recyclerView != null) {
            recyclerView.layoutManager= GridLayoutManager(activity,2)
        }
        var itemList:ArrayList<itemMenu>
        itemList= ArrayList()

        itemList.add(itemMenu(R.drawable.bg_news,"Đọc báo","Luyện tập kỹ năng đọc"))
        itemList.add(itemMenu(R.drawable.bg_sub,"Vietsub","Dịch các bài đọc mẫu"))
        itemList.add(itemMenu(R.drawable.bg_noti,"Thông báo","Thông báo nhắc nhở"))
        itemList.add(itemMenu(R.drawable.bg_tip,"Mẹo","Mẹo TOEIC hữu ích"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Trò chơi luyện từ vựng"))
        itemList.add(itemMenu(R.drawable.bg_dic,"Từ điển","Tra cứu từ khó"))


        adapter= MenuAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }

        adapter.setOnItemClickListener(object: MenuAdapter.onItemClickListener{
            lateinit var intent:Intent
            override fun onItemClick(position: Int) {
                when(position){
                    0->{
                        intent= Intent(context, NewsActivity::class.java)
                    }
                    1->{
                        intent= Intent(context, VietsubActivity::class.java)
                    }
                    2->{
                        intent= Intent(context, NotificationDetailActivity::class.java)

                    }
                    3->{
                        intent= Intent(context, TipListActivity::class.java)

                    }
                    4->{
                        intent= Intent(context, QuizActivity::class.java)
                    }
                    5->{
                        intent= Intent(context, DictionaryActivity::class.java)
                    }

                }
                startActivity(intent)

            }
        })

    }
}