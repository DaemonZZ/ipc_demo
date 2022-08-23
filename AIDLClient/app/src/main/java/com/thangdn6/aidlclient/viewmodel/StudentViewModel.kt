package com.thangdn6.aidlclient.viewmodel

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thangdn6.aidlclient.MainActivity
import com.thangdn6.aidlserver.IListener
import com.thangdn6.aidlserver.IServiceBinder
import com.thangdn6.aidlserver.model.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class StudentViewModel @Inject constructor(private val app:Application): ViewModel() {

    companion object{
        private const val TAG = "ThangDN6 - StudentViewModel"
    }
    @Inject lateinit var scope: CoroutineScope

    private val students : MutableLiveData<List<Student>> by lazy {
        MutableLiveData<List<Student>>().also {
            scope.launch {
                connectToRemote()
            }
        }
    }

    fun getStudents():LiveData<List<Student>>{
        return  students
    }



    private var remoteService: IServiceBinder? = null

    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: Connected")
            remoteService = IServiceBinder.Stub.asInterface(service)
            isBound = true
            remoteService?.sayHello("Xin trào")
            students.postValue(getData())

            remoteService?.setStudentInforChangedListener(object : IListener.Stub(){
                override fun onChange() {
                    scope.launch {
                        students.postValue(getData())
                    }

                }

                override fun notify(message: String?) {
                    Log.d(TAG, "notify: $message")
                }

            })
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: Disconnected")
            isBound = false
        }

    }

    private  fun connectToRemote(){
        val int = Intent().apply {
            setClassName("com.thangdn6.aidlserver","com.thangdn6.aidlserver.service.AIDLService")
        }
        app.bindService(int,connection, Context.BIND_AUTO_CREATE)
        Log.d(TAG, "connectToRemote: ")

    }

    private  fun getData():List<Student>{
        val list = remoteService?.listStudents
        Log.i(TAG, "getData: ${list?.size}")
       return list?: listOf()
    }

    fun updateData(student: Student,flag:Int){
        remoteService?.saveStudent(student,flag)
    }

}