package com.thangdn6.aidlclient.listener

import com.thangdn6.aidlserver.model.Student

interface OnRecyclerItemClicked {
    fun onClick(std: Student)
}