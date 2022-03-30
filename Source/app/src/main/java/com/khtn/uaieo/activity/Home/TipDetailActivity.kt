package com.khtn.uaieo.activity.Home

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Tip
import kotlinx.android.synthetic.main.activity_tip_detail.*

class TipDetailActivity : AppCompatActivity() {
    lateinit var readingArrayList: ArrayList<Tip>
    lateinit var dialog: ProgressDialog
    var message: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip_detail)

        val intent = intent
        message = intent.getStringExtra("TipIndex").toString()

        readingArrayList = ArrayList<Tip>()

        tipTitleDetailTV.setMovementMethod(ScrollingMovementMethod())
        tipContentTV.setMovementMethod(ScrollingMovementMethod())
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

                tipTitleDetailTV.text = readingArrayList[message.toInt()].title
                tipContentTV.text = readingArrayList[message.toInt()].content

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}