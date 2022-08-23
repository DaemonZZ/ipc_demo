package com.thangdn6.aidlclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.thangdn6.aidlclient.adapter.StudentAdapter
import com.thangdn6.aidlclient.databinding.ActivityMainBinding
import com.thangdn6.aidlclient.extension.UIHelper
import com.thangdn6.aidlclient.fragment.EditDialog
import com.thangdn6.aidlclient.fragment.InputDialog
import com.thangdn6.aidlclient.listener.OnDataReturnedListener
import com.thangdn6.aidlclient.listener.OnRecyclerItemClicked
import com.thangdn6.aidlclient.viewmodel.StudentViewModel
import com.thangdn6.aidlserver.model.Student
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnRecyclerItemClicked,OnDataReturnedListener {
    companion object{
        private const val TAG = "ThangDN6 - MainActivity"
    }
    val viewModel by viewModels<StudentViewModel>()
    private var listStds = listOf<Student>()
    private lateinit var binding:ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UIHelper.setupUI(binding.root,this)
        observe()


    }

    private fun observe() {
        viewModel.getStudents().observe(this,{
            listStds = it
           updateRecycler(listStds)
            Log.i(TAG, "observe: JUSTUPDATE")
        })
    }




    fun searchButtonClick(v:View){
        val input = binding.edtSearch.text.toString()
        if(input!=""){
            val filteredList = listStds.filter { it.name.contains(input) }
            updateRecycler(filteredList)
        }else{
            updateRecycler(listStds)
        }

    }

    fun addButtonClick(v:View){
        val dialog = InputDialog.newInstance(Student().apply { id= listStds.size+1 })
        dialog.setOnReturnDataListener(this)
        dialog.show(supportFragmentManager,"add")
    }


    private fun updateRecycler(list: List<Student>) {
        val adapter = StudentAdapter(list)
        adapter.setOnItemClicked(this)
        binding.rvStd.adapter = adapter
        binding.rvStd.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }


    //OnRecyclerItemClicked
    override fun onClick(std: Student) {
        val dialog = EditDialog.newInstance(std)
        dialog.setOnReturnDataListener(this)
        dialog.show(supportFragmentManager,"add")
    }


    //OnDataReturnedListener
    override fun onSubmit(std: Student, flag: Int) {
        viewModel.updateData(std, flag)
    }

}