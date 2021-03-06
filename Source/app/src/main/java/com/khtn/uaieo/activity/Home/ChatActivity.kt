package com.khtn.uaieo.activity.Home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.ChatAdapter
import com.khtn.uaieo.model.ChatMessage
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.HashMap

class ChatActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var chatArray= ArrayList<ChatMessage>()
    lateinit var adapter:ChatAdapter
    lateinit var customListView: RecyclerView
    var topic=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val intent=intent

        topic= intent.getStringExtra("topic").toString()
        Log.d("MyScreen",topic)
        clickChat()
        loadData()
        setupLayout()
        input.setOnClickListener {
            customListView.scrollToPosition(chatArray.size-1)
        }
    }

    private fun setupLayout() {
        adapter= ChatAdapter(this,chatArray)
        customListView = findViewById<RecyclerView>(R.id.chatRV) as RecyclerView
        customListView!!.adapter = adapter

        customListView.layoutManager = LinearLayoutManager(this)
    }

    private fun loadData() {
        val ref= FirebaseDatabase.getInstance().getReference("chat").child(topic)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatArray.clear()
                for (item in snapshot.children){
                    val chat = item.getValue(ChatMessage::class.java)
                    if(chat!!.idSender==currentUser!!.uid){
                        chat.type=1
                    }else{
                        chat.type=0
                    }
                    chatArray.add(chat!!)
                }
                for(chat in chatArray){
                    Log.d("MyScreen",chat.idSender.toString())
                    Log.d("MyScreen",chat.type.toString())

                }
                adapter.notifyDataSetChanged()
                customListView.scrollToPosition(chatArray.size-1)
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

            databaseReference!!.child("chat").child(topic).child(chatID).setValue(hashMap)
            // Clear the input
            input.setText("")
        }
    }

}