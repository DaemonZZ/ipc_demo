package com.thangdn6.aidlclient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.thangdn6.aidlclient.databinding.DialogDescBinding
import com.thangdn6.aidlclient.listener.OnDataReturnedListener
import com.thangdn6.aidlclient.util.ActionFlags
import com.thangdn6.aidlserver.model.Student

class InputDialog : DialogFragment() {

    private lateinit var binding: DialogDescBinding
    private lateinit var listener: OnDataReturnedListener

    companion object {
        private const val TAG = "ThangDN6 - InputDialog"

        fun newInstance(std: Student): InputDialog {
            val dialog = InputDialog()
            val arg = Bundle().apply { putParcelable("student", std) }
            dialog.arguments = arg
            return dialog
        }
    }

    fun setOnReturnDataListener(listener: OnDataReturnedListener){
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogDescBinding.inflate(inflater)
        initValue()
        binding.btnOk.setOnClickListener { onSubmit() }
        binding.btnCancel.setOnClickListener { onCancel() }
        return binding.root
    }

    private fun initValue() {
        val input = arguments?.getParcelable<Student>("student")
        if (input != null) {
            binding.student = input
        }
    }

    private fun isValid(std: Student): Boolean {
        if (std.name == "") {
            Toast.makeText(context, "Fill the name", Toast.LENGTH_SHORT).show()
            return false
        }
        if (std.age < 18 || std.age > 70) {
            Toast.makeText(context, "What the age?", Toast.LENGTH_SHORT).show()
            return false
        }
        if (std.chemistry < 0 || std.chemistry > 10) {
            Toast.makeText(context, "chemistry value is not allowed", Toast.LENGTH_SHORT).show()
            return false
        }
        if (std.math < 0 || std.math > 10) {
            Toast.makeText(context, "math value is not allowed", Toast.LENGTH_SHORT).show()
            return false
        }
        if (std.physic < 0 || std.physic > 10) {
            Toast.makeText(context, "physic value is not allowed", Toast.LENGTH_SHORT).show()
            return false
        }
        if (std.english < 0 || std.english > 10) {
            Toast.makeText(context, "english value is not allowed", Toast.LENGTH_SHORT).show()
            return false
        }
        if (std.literature < 0 || std.literature > 10) {
            Toast.makeText(context, "literature value is not allowed", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun onCancel() {
        dismiss()
    }

    private fun onSubmit() {
        val std = Student()
        std.name = binding.edtName.text.toString()
        std.age = binding.edtAge.text.toString().toInt()
        std.math=binding.edtMath.text.toString().toFloat()
        std.physic=binding.edtPhysic.text.toString().toFloat()
        std.chemistry=binding.edtChemistry.text.toString().toFloat()
        std.english=binding.edtEnglish.text.toString().toFloat()
        std.literature=binding.edtLiterature.text.toString().toFloat()
        if(isValid(std)){
            listener.onSubmit(std,ActionFlags.FLAG_INSERT)
            dismiss()
        }
    }
}