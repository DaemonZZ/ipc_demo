package com.thangdn6.aidlclient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.thangdn6.aidlclient.databinding.DialogEditBinding
import com.thangdn6.aidlclient.listener.OnDataReturnedListener
import com.thangdn6.aidlclient.util.ActionFlags
import com.thangdn6.aidlserver.model.Student

class EditDialog: DialogFragment() {
    private lateinit var listener: OnDataReturnedListener
    private lateinit var binding: DialogEditBinding

    companion object{
        private const val TAG = "ThangDN6 - EditDialog"
        fun newInstance(std: Student): EditDialog {
            val dialog = EditDialog()
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
        binding = DialogEditBinding.inflate(inflater)
        bind()
        return binding.root
    }

    private fun bind() {
        binding.btnSave.setOnClickListener { onSave() }
        binding.btnDelete.setOnClickListener { onDelete() }
        binding.btnCancel.setOnClickListener { onCancel() }
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

    private fun onDelete() {
        val std = binding.student!!
        listener.onSubmit(std, ActionFlags.FLAG_DELETE)
        dismiss()
    }

    private fun onSave() {
        val std = binding.student!!
        std.name = binding.edtName.text.toString()
        std.age = binding.edtAge.text.toString().toInt()
        std.math=binding.edtMath.text.toString().toFloat()
        std.physic=binding.edtPhysic.text.toString().toFloat()
        std.chemistry=binding.edtChemistry.text.toString().toFloat()
        std.english=binding.edtEnglish.text.toString().toFloat()
        std.literature=binding.edtLiterature.text.toString().toFloat()
        if(isValid(std)){
            listener.onSubmit(std, ActionFlags.FLAG_UPDATE)
            dismiss()
        }
    }
}