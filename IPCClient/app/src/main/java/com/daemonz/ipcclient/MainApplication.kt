package com.daemonz.ipcclient

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.widget.Toast

class MainApplication:Application() {
    companion object{
        const val CHANNEL_ID = "ipc_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createChannelNotification()
    }

    private fun createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID,"ICPServer Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(notificationChannel)
        } else {
            Toast.makeText(this, "Android version not support channel notification", Toast.LENGTH_SHORT).show()
        }
    }
}