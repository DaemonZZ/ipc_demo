package com.thangdn6.aidlserver.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.thangdn6.aidlserver.IListener
import com.thangdn6.aidlserver.IServiceBinder
import com.thangdn6.aidlserver.R
import com.thangdn6.aidlserver.activity.MainActivity
import com.thangdn6.aidlserver.application.AIDLApplication.Companion.CHANNEL_ID
import com.thangdn6.aidlserver.database.dao.StudentDAO
import com.thangdn6.aidlserver.model.Student
import com.thangdn6.aidlserver.util.ActionFlags
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AIDLService : Service() {

    companion object {
        private const val TAG = "ThangDN6 - AIDLService"
        private const val ACTION_STOP_SERVICE = "stop"
    }

    private lateinit var listener: IListener

    @Inject
    lateinit var stdDAO: StudentDAO

    @Inject
    lateinit var scope: CoroutineScope

    private lateinit var listStd: List<Student>


    private val binder = object : IServiceBinder.Stub() {
        override fun sayHello(s: String?) {
            Log.d(TAG, "sayHello: $s")
        }

        override fun setStudentInforChangedListener(listener: IListener) {
            this@AIDLService.listener = listener
        }

        override fun getListStudents(): List<Student> {

            return listStd
        }

        override fun saveStudent(std: Student?, flag: Int) {
            if (std != null) {
                when (flag) {
                    ActionFlags.FLAG_DELETE -> deleteStd(std)
                    ActionFlags.FLAG_INSERT -> insertStd(std)
                    ActionFlags.FLAG_UPDATE -> updateStd(std)
                    else -> listener.notify("flag must be ActionFlags.FLAG_DELETE,ActionFlags.FLAG_INSERT or ActionFlags.FLAG_UPDATE")
                }
            }

        }

        override fun getStdById(id: Int): Student {
            val std = listStd.first { it.id == id }
            Log.i(TAG, "getStdById: ${std.name}    ${std.math}")
            return std
        }

    }

    private fun updateStd(std: Student) {
        scope.launch {
            stdDAO.updateStd(std)
            listStd = stdDAO.getListStds()
            listener.notify("Lưu dữ liệu thành công")
            listener.onChange()
        }
    }

    private fun insertStd(std: Student) {
        scope.launch {
            stdDAO.insertStd(std)
            listStd = stdDAO.getListStds()
            listener.notify("Thêm mới dữ liệu thành công")
            listener.onChange()
        }
    }

    private fun deleteStd(std: Student) {
        scope.launch {
            stdDAO.deleteStd(std)
            listStd = stdDAO.getListStds()
            listener.notify("Xóa dữ liệu thành công")
            listener.onChange()
        }
    }

    override fun onCreate() {
        startService()
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        scope.launch {
            listStd = stdDAO.getListStds()
        }
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(ACTION_STOP_SERVICE == intent?.action){
            stopForeground(true)
            stopSelf()
        }
        return START_NOT_STICKY
    }

    @SuppressLint("LaunchActivityFromNotification", "UnspecifiedImmutableFlag")
    private fun startService(){
        val intent = Intent(this, AIDLService::class.java).apply {
            action = ACTION_STOP_SERVICE
        }
        val pendingIntent = PendingIntent.getService(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("AIDL Server")
            .setContentText("Tap to stop")
            .setContentIntent(pendingIntent)
            .build()


        startForeground(12,notification)
    }
}