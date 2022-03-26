package com.khtn.uaieo.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
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
        itemList.add(itemMenu(R.drawable.bg_schedule,"Lập lịch","Lâp lịch thi TOEIC"))
        itemList.add(itemMenu(R.drawable.bg_tip,"Mẹo","Mẹo TOEIC hữu ích"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Trò chơi luyện từ vựng"))
        itemList.add(itemMenu(R.drawable.bg_dic,"Từ điển","Tra cứu từ khó"))

        adapter= MenuAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }



    }
}