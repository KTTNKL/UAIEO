package com.khtn.uaieo.activity.Speaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.ProgressDialog
import android.media.MediaPlayer
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_detail_answer_speaking.*
import kotlinx.android.synthetic.main.activity_part1.*

class DetailAnswerSpeakingActivity : AppCompatActivity() {

    private  val storageReference= FirebaseStorage.getInstance().reference
    private val databaseReference = FirebaseDatabase.getInstance().reference
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    var path: String = ""
    var media= MediaPlayer()
    var audio: String = ""
    lateinit var readingArrayList: ArrayList<Comment>
    lateinit var dialog: ProgressDialog
    lateinit var newRecyclerview: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_answer_speaking)

        var question = intent.getSerializableExtra("AnswerData") as WritingSpeakingExample
        path = intent.getStringExtra("path").toString();


        authorSpeakingAnswerTV.text = "Làm của: " + question.email
        readingArrayList = ArrayList<Comment>()

        newRecyclerview = findViewById(R.id.commentRecyclerView1)
        newRecyclerview.layoutManager = LinearLayoutManager(this)
        newRecyclerview.setHasFixedSize(true)


        dialog = ProgressDialog.show(
            this, "",
            "Loading Reading. Please wait...", true
        )

        LoadData()


        commentBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            user= auth.currentUser!!

            val cmtID = "" + System.currentTimeMillis()
            val reference= FirebaseDatabase.getInstance().reference!!.child(path + "/comment/" + cmtID)

            user.email?.let { it1 -> reference.child("email").setValue(it1) }
            reference.child("content").setValue("${commentTV.text}")

            LoadData()
        }
        audioBtn.setOnClickListener{

            if(!media.isPlaying){
                media.setDataSource(question.image.toString())
                media.prepare()
                media.start()
                audioBtn.setText("DỪNG")
            }else{
                media.stop()
                media.reset()
                audioBtn.setText("PHÁT")
            }
        }
        media.setOnCompletionListener {
            media.stop();
            if (media != null) {
                media.reset();
                audioBtn.setText("PHÁT")
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (media != null) {
        media.stop()
        media.release()}
        finish()
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