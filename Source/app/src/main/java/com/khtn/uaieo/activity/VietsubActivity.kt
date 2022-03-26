package com.khtn.uaieo.activity
import android.app.ProgressDialog
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.khtn.uaieo.R
import com.khtn.uaieo.model.Vietsub
import kotlinx.android.synthetic.main.activity_vietsub.*


class VietsubActivity : AppCompatActivity() {
    lateinit var readingArrayList: ArrayList<Vietsub>
    lateinit var dialog: ProgressDialog
    var currentIndex: Int = 0
    var totalReading: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vietsub)
        readingArrayList = ArrayList<Vietsub>()
        EnReadingTV.setMovementMethod(ScrollingMovementMethod())
        ViReadingTV.setMovementMethod(ScrollingMovementMethod())
        titleReadingTV.setMovementMethod(ScrollingMovementMethod())
        dialog = ProgressDialog.show(
            this, "",
            "Loading Reading. Please wait...", true
        )

        backBtn.visibility= View.INVISIBLE


        LoadData()
        backBtn.setOnClickListener{
            if(currentIndex > 0){
                currentIndex--;

                nextBtn.visibility= View.VISIBLE
                if(currentIndex == 0)   backBtn.visibility= View.INVISIBLE

                titleReadingTV.text = readingArrayList.get(currentIndex).title
                EnReadingTV.text = readingArrayList.get(currentIndex).enReading
                ViReadingTV.text = readingArrayList.get(currentIndex).viReading
            }
        }

        nextBtn.setOnClickListener{
            if(currentIndex < (totalReading-1)){
                currentIndex++;

                backBtn.visibility= View.VISIBLE
                if(currentIndex == (totalReading-1))   nextBtn.visibility= View.INVISIBLE

                titleReadingTV.text = readingArrayList.get(currentIndex).title
                EnReadingTV.text = readingArrayList.get(currentIndex).enReading
                ViReadingTV.text = readingArrayList.get(currentIndex).viReading
            }
        }
    }
    fun LoadData(){
        val ref= FirebaseDatabase.getInstance().getReference("vietsub")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //Xoa list trc khi them vao moi lan vao app
                readingArrayList.clear()
                for (vietsub in snapshot.children){
                    val modelVideo = vietsub.getValue(Vietsub::class.java)
                    readingArrayList.add(modelVideo!!)
                }
                totalReading = readingArrayList.size;
                dialog.dismiss()
                titleReadingTV.text = readingArrayList.get(currentIndex).title
                EnReadingTV.text = readingArrayList.get(currentIndex).enReading
                ViReadingTV.text = readingArrayList.get(currentIndex).viReading

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}