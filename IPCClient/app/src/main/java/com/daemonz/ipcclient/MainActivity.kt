package com.daemonz.ipcclient

import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivityClient"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Intent(this,IPCClientService::class.java).also {
            startForegroundService(it)
            finishAndRemoveTask()
        }
    }
}