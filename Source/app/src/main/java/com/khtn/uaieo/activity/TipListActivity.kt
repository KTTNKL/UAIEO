package com.khtn.uaieo.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.TipAdapter
import com.khtn.uaieo.model.Tip
import com.khtn.uaieo.model.Vietsub
import kotlinx.android.synthetic.main.activity_tip_list.*
import kotlinx.android.synthetic.main.activity_vietsub.*

class TipListActivity : AppCompatActivity() {

    lateinit var readingArrayList: ArrayList<Tip>
    lateinit var dialog: ProgressDialog
    lateinit var newRecyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_list)

        readingArrayList = ArrayList<Tip>()

        newRecyclerview = findViewById(R.id.tipRecyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)

        dialog = ProgressDialog.show(
            this, "",
            "Loading Reading. Please wait...", true
        )

        LoadData()
    }

    fun LoadData(){
        val ref= FirebaseDatabase.getInstance().getReference("tips")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                readingArrayList.clear()
                for (vietsub in snapshot.children){
                    val modelTip = vietsub.getValue(Tip::class.java)
                    if (modelTip != null) {
                        modelTip.createSubtitle()
                    }
                    readingArrayList.add(modelTip!!)
                }
                dialog.dismiss()

                var adapter = TipAdapter(readingArrayList)
                newRecyclerview.adapter = adapter
                adapter.setOnItemClickListener(object: TipAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        swapScreen(position)
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun swapScreen(position:Int){
        val intent = Intent(this, TipDetailActivity::class.java)
        intent.putExtra("TipIndex", position.toString())
        startActivityForResult(intent, 1111)
    }
}