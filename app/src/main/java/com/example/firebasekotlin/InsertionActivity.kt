package com.example.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etStudentName : EditText
    private lateinit var etStudentGrade : EditText
    private lateinit var etFeedback : EditText
    private lateinit var btnSaveData : Button

    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etStudentName = findViewById(R.id.etEmpName)
        etStudentGrade = findViewById(R.id.etEmpAge)
        etFeedback = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("Students")

        btnSaveData.setOnClickListener {
            saveStudentData()
        }

    }

    private fun saveStudentData() {
        //getting values
        val stName = etStudentName.text.toString()
        val stGrade = etStudentGrade.text.toString()
        val stFeedback= etFeedback.text.toString()

        if (stName.isEmpty()){
            etStudentName.error = "Please enter name"
        }
        if (stGrade.isEmpty()){
            etStudentGrade.error = "Please enter grade"
        }
        if (stFeedback.isEmpty()){
            etFeedback.error = "Please enter feedback"
        }


        val Id = dbRef.push().key!!

        val student = StudentModel(Id,stName,stGrade,stFeedback)

        dbRef.child(Id).setValue(student)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etStudentName.text.clear()
                etStudentGrade.text.clear()
                etFeedback.text.clear()

            }.addOnFailureListener {
                err -> Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}