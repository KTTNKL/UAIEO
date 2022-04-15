package com.khtn.uaieo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.AnalystUserAdapter
import com.khtn.uaieo.adapter.TipAdapter
import com.khtn.uaieo.model.itemAnalystUser
import com.khtn.uaieo.model.itemExamRL

class AnalystUserActivity : AppCompatActivity() {
    var id=""
    var arrUser= ArrayList<itemAnalystUser>()
    lateinit var RV: RecyclerView
    lateinit var adapter:AnalystUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analyst_user)
        id= intent.getStringExtra("id").toString()
        loadData()
        setupLayout()
    }

    private fun setupLayout() {
        adapter=AnalystUserAdapter(arrUser)
        RV = findViewById(R.id.anlystUserRV)
        RV.layoutManager = LinearLayoutManager(this)
        RV.adapter = adapter
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(this,
            DividerItemDecoration.VERTICAL)
        RV.addItemDecoration(itemDecoration)
    }

    private fun loadData() {
        val ref= FirebaseDatabase.getInstance().getReference("analyst/${id}")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                arrUser.clear()
                for (item in snapshot.children){
                    val modelUser = item.getValue(itemAnalystUser::class.java)
                    var sum=0
                    sum+= modelUser?.part1!!
                    sum+= modelUser?.part2!!
                    sum+= modelUser?.part3!!
                    sum+= modelUser?.part4!!
                    sum+= modelUser?.part5!!
                    sum+= modelUser?.part6!!
                    sum+= modelUser?.part7!!
                    modelUser.overall=sum
                    arrUser.add(modelUser!!)
                }
                for( i in 0..arrUser.size){
                    for(j in i+1..arrUser.size-1){
                        if(arrUser[i].overall!! < arrUser[j].overall!!){
                            var temp=arrUser[i]
                            arrUser[i]=arrUser[j]
                            arrUser[j]=temp
                        }
                    }
                }
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}