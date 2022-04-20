package com.khtn.uaieo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.khtn.uaieo.R
import java.util.HashMap

class ChatActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener {
            val input = findViewById<View>(R.id.input) as EditText
            val content: String = input.text.toString()

            // Read the input field and push a new instance
            // of ChatMessage to the Firebase database
            auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            databaseReference = FirebaseDatabase.getInstance().reference

            var message = ChatMessage(content,
                currentUser?.email.toString(), currentUser?.uid.toString())

            var hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("idSender", message.idSender.toString())
            hashMap.put("content", message.content.toString())
            hashMap.put("email", message.email.toString())
            hashMap.put("date", message.time.toString())

            databaseReference!!.child("chat").setValue(hashMap)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "Sent successfully", Toast.LENGTH_LONG)
                        .show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
                }

            // Clear the input
            input.setText("")
        }
    }
}