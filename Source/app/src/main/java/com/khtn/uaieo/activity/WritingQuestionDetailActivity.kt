package com.khtn.uaieo.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.khtn.uaieo.R
import com.khtn.uaieo.model.partSW
import kotlinx.android.synthetic.main.activity_speaking_question_detail_acticity.*
import kotlinx.android.synthetic.main.activity_writing_question_detail.*

class WritingQuestionDetailActivity : AppCompatActivity() {
    var thumbnail: Uri? =null
    var ID: String? = ""
    private  val storageReference= FirebaseStorage.getInstance().reference
    private val databaseReference = FirebaseDatabase.getInstance().reference
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writing_question_detail)

        var question = intent.getSerializableExtra("WritingQuestionData") as partSW

        ID = intent.getStringExtra("IDExam");
        Glide.with(this).load(question.image).into(writingImage)
        writingQuestionNumberTV.text = "CÂU: " + question.number.toString()
        writingContentQuestionTV.text = question.content
        writingDirectionTV.text = question.directions
        writingWordTV.text = question.words

        exampleAnswerBtn.setOnClickListener {
//            val intent = Intent(this, ExampleWritingAnswerListActivity::class.java)
//            intent.putExtra("IdExam",ID);
//            intent.putExtra("QuestionNumber",question.number.toString());
//            startActivityForResult(intent, 1111)
        }
        pickImage.setOnClickListener {
            startFileChooser()
        }
        uploadImageBtn.setOnClickListener {
            auth = FirebaseAuth.getInstance()
            user= auth.currentUser!!

            val imageID = "" + System.currentTimeMillis()
            if (thumbnail==null){
                Toast.makeText(this,"You must pick picture from gallery first!", Toast.LENGTH_LONG).show()
            }else{
                val path = "${ID}/${question.number}/${imageID}.png"
                val uploadTask = thumbnail?.let { storageReference.child(path).putFile(it) }
                if (uploadTask != null) {
                    uploadTask.addOnSuccessListener {
                        val downloadURLTask = storageReference.child(path).downloadUrl
                        downloadURLTask.addOnSuccessListener {
                            var hashMap: HashMap<String, Any> = HashMap()
                            hashMap.put("image", it.toString())
                            hashMap.put("id", user.uid)
                            user.email?.let { it1 -> hashMap.put("email", it1) }

                            databaseReference.child("WSquestions/writing/${ID}/question${question.number}").child("example").child(user.uid).setValue(hashMap)
                                .addOnSuccessListener { taskSnapshot ->
                                    Toast.makeText(this, "uploaded successfully", Toast.LENGTH_LONG)
                                        .show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
                                }
                        }

                    }
                }
            }
        }
    }

    fun startFileChooser() {
        var i= Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(i,1111)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1111 &&resultCode== Activity.RESULT_OK&& data!=null){
            thumbnail=data.data!!
            uploadImage.setImageURI(thumbnail)
        }

    }

}