package com.khtn.uaieo.activity

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.khtn.uaieo.R
import com.khtn.uaieo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_notification_detail.*
import kotlinx.android.synthetic.main.activity_notification_detail.view.*
import java.util.*

class NotificationDetailActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var title: String? = ""
    var content: String? = ""
    var day: Int? = 0
    var month: Int? = 0
    var year: Int? = 0
    var hour: Int? = 0
    var minute: Int? = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_detail)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        databaseReference = FirebaseDatabase.getInstance().reference


        createNotificationChannel()
        submitBT.setOnClickListener { scheduleNotification()

            var hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("title", title!!)
            hashMap.put("content", content!!)
            hashMap.put("day", day!!)
            hashMap.put("month", month!!)
            hashMap.put("year", year!!)
            hashMap.put("hour", hour!!)
            hashMap.put("minute", minute!!)

            databaseReference!!.child("profile").child(currentUser!!.uid).child("schedule").setValue(hashMap)
                .addOnSuccessListener { taskSnapshot ->
                    Toast.makeText(this, "Scheduled successfully", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "${e.message}", Toast.LENGTH_LONG).show()
                }

            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        title = titleET.text.toString()
        content = messageET.text.toString()
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, content)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        showAlert(time, title!!, content!!)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " +
                        timeFormat.format(date))
            .setPositiveButton("Okay"){_, _ ->}
            .show()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun getTime(): Long {
        minute = timePicker.minute
        hour = timePicker.hour
        day = datePicker.dayOfMonth
        month = datePicker.month
        year = datePicker.year



        val canlendar = Calendar.getInstance()
        canlendar.set(year!!, month!!, day!!, hour!!, minute!!)

        return canlendar.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}