package com.example.firebasekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.firebasekotlin.R
import com.example.firebasekotlin.models.StudentModel
import com.google.firebase.database.FirebaseDatabase

class StudentsDetailsActivity : AppCompatActivity() {

    private lateinit var tvId: TextView
    private lateinit var tvStudentName: TextView
    private lateinit var tvStudentGrade: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("Id").toString(),
                intent.getStringExtra("etStudentName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("Id").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Students").child(id)
        val mTask = dbRef.removeValue()
        
        mTask.addOnSuccessListener {
            Toast.makeText(this, "Student data deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {
            error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(Id: String, etStudentName: String) {

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)

        mDialog.setView(mDialogView)

        val etStudentName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etStudentGrade = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etFeedback = mDialogView.findViewById<EditText>(R.id.etEmpSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etStudentName.setText(intent.getStringExtra("etStudentName").toString())
        etStudentGrade.setText(intent.getStringExtra("etStudentGrade").toString())
        etFeedback.setText(intent.getStringExtra("etFeedback").toString())

        mDialog.setTitle("Updating $etStudentName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateStdData(
                Id,
                etStudentName.text.toString(),
                etStudentGrade.text.toString(),
                etFeedback.text.toString()
            )

            Toast.makeText(applicationContext, "Student Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvStudentName.text = etStudentName.text.toString()
            tvStudentGrade.text = etStudentGrade.text.toString()
            tvFeedback.text = etFeedback.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateStdData(
        id: String,
        name: String,
        age: String,
        salary: String) {

        val dbRef = FirebaseDatabase.getInstance().getReference("Students").child(id)
        val empInfo = StudentModel(id,name,age,salary)
        dbRef.setValue(empInfo)
    }

    private fun initView() {
        tvId = findViewById(R.id.tvEmpId)
        tvStudentName = findViewById(R.id.tvEmpName)
        tvStudentGrade = findViewById(R.id.tvEmpAge)
        tvFeedback = findViewById(R.id.tvEmpSalary)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvId.text = intent.getStringExtra("Id")
        tvStudentName.text = intent.getStringExtra("etStudentName")
        tvStudentGrade.text = intent.getStringExtra("etStudentGrade")
        tvFeedback.text = intent.getStringExtra("etFeedback")

    }
}
