package com.khtn.uaieo.activity

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.khtn.uaieo.R
import com.khtn.uaieo.adapter.CommentAdapter
import com.khtn.uaieo.model.Comment
import com.khtn.uaieo.model.WritingSpeakingExample
import kotlinx.android.synthetic.main.activity_detail_answer.*

class DetailAnswerWritingActivity : AppCompatActivity() {

    private  val storageReference= FirebaseStorage.getInstance().reference
    private val databaseReference = FirebaseDatabase.getInstance().reference
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    var path: String = ""
    lateinit var readingArrayList: ArrayList<Comment>
    lateinit var dialog: ProgressDialog
    lateinit var newRecyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_answer)

        var question = intent.getSerializableExtra("AnswerData") as WritingSpeakingExample
        path = intent.getStringExtra("path").toString();

        Glide.with(this).load(question.image).into(answerImage)
        authorAnswerTV.text = "Bài làm: " + question.email

        readingArrayList = ArrayList<Comment>()

        newRecyclerview = findViewById(R.id.commentRecyclerView)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)


        dialog = ProgressDialog.show(
            this, "",
            "Loading, Please wait...", true
        )

        LoadData()


        commentBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            user= auth.currentUser!!

            val cmtID = "" + System.currentTimeMillis()
            val reference= FirebaseDatabase.getInstance().reference!!.child(path + "/comment/" + cmtID)

            user.email?.let { it1 -> reference.child("email").setValue(it1) }
            reference.child("content").setValue("${commentTV.text}")
            commentTV.setText("")
            LoadData()
        }
    }
    fun LoadData(){

        //Xoa list trc khi them vao moi lan vao app

        val ref= FirebaseDatabase.getInstance().getReference(path + "/comment")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                readingArrayList.clear()
                for (temp in snapshot.children){
                    val question = temp.getValue(Comment::class.java)
                    readingArrayList.add(question!!)
                }

                dialog.dismiss()
                var adapter = CommentAdapter(readingArrayList)
                newRecyclerview.adapter = adapter
                adapter.setOnItemClickListener(object: CommentAdapter.onItemClickListener{
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
//        val intent = Intent(this, DetailAnswerActivity::class.java)
//        intent.putExtra("path", path + "/" + readingArrayList.get(position).id)
//        intent.putExtra("AnswerData", readingArrayList.get(position))
//        startActivityForResult(intent, 1111)
    }
}