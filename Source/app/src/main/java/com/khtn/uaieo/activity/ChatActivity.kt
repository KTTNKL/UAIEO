package com.khtn.uaieo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.khtn.uaieo.R
import com.khtn.uaieo.activity.ReadingListening.PartRLExamActivity
import com.khtn.uaieo.adapter.ChatAdapter
import com.khtn.uaieo.adapter.RLExamAdapter
import com.khtn.uaieo.model.ChatMessage
import com.khtn.uaieo.model.itemExamRL
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.HashMap

class ChatActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var chatArray= ArrayList<ChatMessage>()
    lateinit var adapter:ChatAdapter
    lateinit var customListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        clickChat()
        loadData()
        setupLayout()
    }

    private fun setupLayout() {
        adapter= ChatAdapter(chatArray)
        customListView = findViewById<RecyclerView>(R.id.chatRV) as RecyclerView
        customListView!!.adapter = adapter

        customListView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadData() {
        val ref= FirebaseDatabase.getInstance().getReference("chat")
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatArray.clear()
                for (item in snapshot.children){
                    val chat = item.getValue(ChatMessage::class.java)
                    chatArray.add(chat!!)
                    Log.d("MyScreen",chat.time.toString())
                }
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun clickChat() {
        fab.setOnClickListener {
            val input = findViewById<View>(R.id.input) as EditText
            val content: String = input.text.toString()

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            databaseReference = FirebaseDatabase.getInstance().reference

            var message = ChatMessage(content, currentUser?.email.toString(), currentUser?.uid.toString())
            val chatID = "" + System.currentTimeMillis()

            var hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("idSender", message.idSender.toString())
            hashMap.put("content", message.content.toString())
            hashMap.put("email", message.email.toString())
            hashMap.put("time", message.time.toString())

            databaseReference!!.child("chat").child(chatID).setValue(hashMap)
            // Clear the input
            input.setText("")
        }    }

}