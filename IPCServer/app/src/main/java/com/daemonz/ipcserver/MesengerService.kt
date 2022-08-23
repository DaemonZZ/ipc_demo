package com.daemonz.ipcserver

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class MessengerService : Service() {
    companion object {
        private const val TAG = "MessengerService"
    }
    private val mHandle = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handleMessage: ${msg.data.getString("test")}")
            val reply = Message.obtain().apply {
                data = Bundle().apply {
                    putString("test","Hello Client")
                }
            }
            msg.replyTo.send(reply)
        }
    }
    private val mMessenger = Messenger(mHandle)

    override fun onCreate() {
        startForeground(10,makeNotification())
        super.onCreate()
    }
    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind: ${intent.action}")
        return mMessenger.binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int)
    : Int {
        return START_STICKY
    }


    private fun makeNotification(): Notification {

        return Notification.Builder(this, MainApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Demo IPC")
            .setContentText("Hello")
            .setOngoing(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }
}