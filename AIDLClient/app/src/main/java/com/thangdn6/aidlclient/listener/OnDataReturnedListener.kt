package com.thangdn6.aidlclient.listener

import com.thangdn6.aidlserver.model.Student

interface OnDataReturnedListener {
    fun onSubmit(std:Student,flag:Int)
}