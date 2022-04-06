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
import com.khtn.uaieo.activity.Home.ScheduleScreen
import com.khtn.uaieo.activity.ReadingListening.ListRLExamActivity
import com.khtn.uaieo.activity.Speaking.SpeakingExamListActivity
import com.khtn.uaieo.activity.WritingExamListActivity
import com.khtn.uaieo.adapter.MenuAdapter
import com.khtn.uaieo.model.itemMenu

class ExamFragment : Fragment() {
    private lateinit var adapter: MenuAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_exam, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.examRecycleView)
        if (recyclerView != null) {
            recyclerView.layoutManager= GridLayoutManager(activity,2)
        }
        var itemList:ArrayList<itemMenu>
        itemList= ArrayList()

        itemList.add(itemMenu(R.drawable.bg_news,"Listening/Reading","Thi đọc và nghe"))
        itemList.add(itemMenu(R.drawable.bg_sub,"Speaking","Thi nói"))
        itemList.add(itemMenu(R.drawable.bg_schedule,"Writing","Thi viết"))



        adapter= MenuAdapter(itemList)
        if (recyclerView != null) {
            recyclerView.adapter=adapter
        }

        adapter.setOnItemClickListener(object: MenuAdapter.onItemClickListener{
            lateinit var intent: Intent
            override fun onItemClick(position: Int) {
                when(position){
                    0->{
                        intent= Intent(context, ListRLExamActivity::class.java)
                    }
                    1->{
                        intent= Intent(context, SpeakingExamListActivity::class.java)
                    }
                    2->{
                        intent= Intent(context, WritingExamListActivity::class.java)

                    }

                }
                startActivity(intent)

            }
        })

    }


}