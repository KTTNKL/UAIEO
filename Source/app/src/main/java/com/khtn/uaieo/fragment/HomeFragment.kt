package com.khtn.uaieo.fragment

import android.content.Intent
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
import com.khtn.uaieo.activity.NewsActivity
import com.khtn.uaieo.activity.QuizActivity
import com.khtn.uaieo.activity.ScheduleScreen
import com.khtn.uaieo.adapter.MenuAdapter
import com.khtn.uaieo.model.Vietsub
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
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Practice Vocab"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Practice Vocab"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Practice Vocab"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Practice Vocab"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Practice Vocab"))
        itemList.add(itemMenu(R.drawable.bg_quiz,"Quiz","Practice Vocab"))

        adapter= MenuAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }

        adapter.setOnItemClickListener(object: MenuAdapter.onItemClickListener{
            lateinit var intent:Intent
            override fun onItemClick(position: Int) {
                when(position){
                    0->{
                        intent= Intent(context,NewsActivity::class.java)
                    }
                    1->{
                        intent= Intent(context,Vietsub::class.java)
                    }
                    2->{
                        intent= Intent(context,ScheduleScreen::class.java)

                    }
                    3->{
                        intent= Intent(context,ScheduleScreen::class.java)

                    }
                    4->{
                        intent= Intent(context,QuizActivity::class.java)
                    }
                    5->{
                        intent= Intent(context,ScheduleScreen::class.java)
                    }

                }
                startActivity(intent)

            }
        })

    }
}