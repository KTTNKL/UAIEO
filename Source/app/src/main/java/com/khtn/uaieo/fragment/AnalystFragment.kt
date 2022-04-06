package com.khtn.uaieo.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.AnalystUserActivity
import com.khtn.uaieo.activity.ReadingListening.PartRLExamActivity
import com.khtn.uaieo.adapter.AnalystExamAdapter
import com.khtn.uaieo.model.itemExamRL
import com.khtn.uaieo.model.itemMenu

class AnalystFragment : Fragment() {

    var itemList=ArrayList<itemExamRL>()

    private lateinit var adapter: AnalystExamAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_analyst, container, false)
        initRecyclerView(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadExam()

    }

    private fun loadExam() {
        val ref= FirebaseDatabase.getInstance().getReference("RLquestions")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                itemList.clear()
                for (item in snapshot.children){
                    val modelExam = item.getValue(itemExamRL::class.java)
                    itemList.add(modelExam!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun initRecyclerView(view: View?) {
        val recyclerView= view?.findViewById<RecyclerView>(R.id.analystExamRV)
        if (recyclerView != null) {
            recyclerView.layoutManager= LinearLayoutManager(activity)
        }
        adapter= AnalystExamAdapter(itemList)
        recyclerView!!.adapter=adapter

        adapter.setOnItemClickListener(object :AnalystExamAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent= Intent(context, AnalystUserActivity::class.java)
                intent.putExtra("id",itemList[position].id)
                startActivity(intent)
            }
        })
    }


}