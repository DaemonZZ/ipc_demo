package com.daemonz.ipcclient

import android.app.Notification
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class IPCClientService : Service() {

    companion object {
        private const val TAG = "IPCClientService"
        private const val ACTION = "com.daemonz.ipcserver.CONNECT_SERVICE"
    }

    private var servertMess: Messenger? = null
    private var clientMess = Messenger(object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Log.d(TAG, "handleMessage: ${msg.data.getString("test")}")
//            val reply = Message.obtain().apply {
//                data = Bundle().apply {
//                    putString("test", "Hello Server")
//                }
//                replyTo = servertMess
//            }
//            msg.replyTo.send(reply)
        }
    })
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
            Log.d(TAG, "onServiceConnected: ")
            servertMess = Messenger(binder)
            val message = Message.obtain().apply {
                data = Bundle().apply {
                    putString("test", "Hello Server ")
                }
                replyTo = clientMess
            }
            servertMess?.send(message)
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
        }

    }

    override fun onCreate() {
        startForeground(10, makeNotification())
        super.onCreate()
        Intent(ACTION).also {
            it.setClassName("com.daemonz.ipcserver", "com.daemonz.ipcserver.MessengerService")
            bindService(it, connection, BIND_AUTO_CREATE)
        }


    }

    override fun onBind(intent: Intent): IBinder? {
        Log.d(TAG, "onBind: ${intent.action}")
        return clientMess.binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int)
            : Int {
        return START_STICKY
    }


    private fun makeNotification(): Notification {

        return Notification.Builder(this, MainApplication.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Demo IPC Client")
            .setContentText("Hello")
            .setOngoing(true)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }
}