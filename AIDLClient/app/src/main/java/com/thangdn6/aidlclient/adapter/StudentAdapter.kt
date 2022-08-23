package com.thangdn6.aidlclient.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thangdn6.aidlclient.databinding.RvItemBinding
import com.thangdn6.aidlclient.listener.OnRecyclerItemClicked
import com.thangdn6.aidlserver.model.Student

class StudentAdapter(private val listStds:List<Student>) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    companion object{
        private const val TAG = "ThangDN6 - StudentAdapter"
    }
    private lateinit var listener: OnRecyclerItemClicked
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder(RvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(listStds[position])

    }

    override fun getItemCount(): Int {
        return listStds.size
    }

    fun setOnItemClicked(listener:OnRecyclerItemClicked){
        this.listener = listener
    }

    inner class StudentViewHolder(private val binding: RvItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(std:Student){
            binding.student = std
            binding.root.setOnClickListener{listener.onClick(binding.student!!)}
        }

    }
}

