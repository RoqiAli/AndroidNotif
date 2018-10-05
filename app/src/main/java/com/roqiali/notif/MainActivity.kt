package com.roqiali.notif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val channelId = "CHANNEL_ID"
    private val notifId = 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            createNotifChannel()

            val intent = Intent(this, ShowActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val builder = NotificationCompat.Builder(this, channelId)
            builder.setSmallIcon(R.drawable.ic_sms)
                    .setContentTitle("Simple notification")
                    .setContentText("This is a simple notification")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .priority = NotificationCompat.PRIORITY_DEFAULT

            val notifMan = NotificationManagerCompat.from(this)
            notifMan.notify(notifId, builder.build())
        }
    }

    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifName = "PERSONAL_NOTIF"

            val notifChannel = NotificationChannel(channelId, notifName, NotificationManager.IMPORTANCE_DEFAULT)
            notifChannel.description = "include all the personal notification"

            val notMan = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notMan.createNotificationChannel(notifChannel)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
