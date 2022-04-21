package com.khtn.uaieo.activity.Speaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.khtn.uaieo.R
import com.khtn.uaieo.model.partSW
import kotlinx.android.synthetic.main.activity_speaking_question_detail_acticity.*
import android.Manifest
import android.app.ProgressDialog
import android.app.appsearch.StorageInfo
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_speaking_question_detail_acticity.*
import android.util.Log
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.khtn.uaieo.activity.ListAnswerWritingActivity
import java.io.File
import java.io.IOException

private const val LOG_TAG = "AudioRecordTest"
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200


class SpeakingQuestionDetailActicity : AppCompatActivity() {
    var mStartRecording = true
    var mStartPlaying = true
    private var fileName: String = ""

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    private  val storageReference= FirebaseStorage.getInstance().reference
    private val databaseReference = FirebaseDatabase.getInstance().reference
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    var mProgress: ProgressDialog?=null
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun onRecord(start: Boolean) = if (start) {
        startRecording()
    } else {
        stopRecording()
    }

    private fun onPlay(start: Boolean) = if (start) {
        startPlaying()
    } else {
        stopPlaying()
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            try {
                setDataSource(fileName)
                prepare()
                start()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }
        }
    }

    private fun stopPlaying() {
        player?.release()
        player = null
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(fileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG_TAG, "prepare() failed")
            }

            start()
        }
    }

    private fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking_question_detail_acticity)

        var id = intent.getStringExtra("SpeakingID").toString()
        var question = intent.getSerializableExtra("SpeakingQuestionData") as partSW

        Log.d("111111111", id)
        Glide.with(this).load(question.image).into(speakingImage)
        questionNumberTV.text = "CÂU: " + question.number.toString()
        contentQuestionTV.text = question.content
        questionTV.text = question.question

        // Record to the external cache directory for visibility
        fileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        btnRecord.setOnClickListener{ // start recording method will
            onRecord(mStartRecording)
            btnRecord.text = when (mStartRecording) {
                true -> "RECORDING ..."
                false -> "NHẤN ĐỂ RECORD"
            }
            mStartRecording = !mStartRecording

        }
        btnPlay.setOnClickListener{ // play audio method will play
            onPlay(mStartPlaying)
            btnPlay.text = when (mStartRecording) {
                true -> "PLAYING..."
                false -> "NGHE LẠI"
            }
            mStartPlaying = !mStartPlaying

        }

        uploadRecordBtn.setOnClickListener{ // play audio method will play
            mProgress = ProgressDialog(this)
            question.number?.let { it1 -> upload(id, it1) }
        }
        otherRecordBtn.setOnClickListener{
            val intent = Intent(this, ListAnswerSpeakingActivity::class.java)
            intent.putExtra("id",id);
            intent.putExtra("number",question.number.toString());
            startActivityForResult(intent, 1111)
        }

    }
    private fun upload(id: String, number: Int){
        mProgress?.setMessage("Uploading ...")
        mProgress?.show()
        auth = FirebaseAuth.getInstance()
        user= auth.currentUser!!

        val recordID = "" + System.currentTimeMillis()+".3gp"
        var path = storageReference.child(id).child(number.toString()).child(recordID.toString())

        var uri: Uri = Uri.fromFile(File(fileName))
        path.putFile(uri).addOnSuccessListener {
            val downloadURLTask = storageReference.child("${id}/${number}/${recordID}").downloadUrl
            downloadURLTask.addOnSuccessListener {
                var hashMap: HashMap<String, Any> = HashMap()
                hashMap.put("image", it.toString())
                hashMap.put("id", user.uid)
                user.email?.let { it1 -> hashMap.put("email", it1) }

                databaseReference.child("WSquestions/speaking/${id}/question${number}").child("example").child(user.uid).setValue(hashMap)
                    .addOnSuccessListener { taskSnapshot ->
                        Toast.makeText(this, "uploaded successfully", Toast.LENGTH_LONG)
                            .show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
                    }
            }
            mProgress?.dismiss()
        }


//        val recordID = "" + System.currentTimeMillis()
//        val path = "speakingExam1/1/${recordID}.3gp"
//        var uri: Uri = Uri.fromFile(File(fileName))
//        val uploadTask = storageReference.child(path).putFile(uri)
//        if (uploadTask != null) {
//            uploadTask.addOnSuccessListener {
//                val downloadURLTask = storageReference.child(path).downloadUrl
//                downloadURLTask.addOnSuccessListener {
//                    var hashMap: HashMap<String, Any> = HashMap()
//                    hashMap.put("record", it.toString())
//                    hashMap.put("id", user.uid)
//                    user.email?.let { it1 -> hashMap.put("email", it1) }
//                    mProgress?.dismiss()
//                    databaseReference.child("WSquestions/speaking/speakingExam1/question1").child("example").child(user.uid).setValue(hashMap)
//                        .addOnSuccessListener { taskSnapshot ->
//                            Toast.makeText(this, "uploaded successfully", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                        .addOnFailureListener { e ->
//                            Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
//                        }
//                }
//
//            }
//        }
    }
    override fun onStop() {
        super.onStop()
        recorder?.release()
        recorder = null
        player?.release()
        player = null
    }

}