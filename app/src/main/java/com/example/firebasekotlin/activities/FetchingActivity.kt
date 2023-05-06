package com.example.firebasekotlin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasekotlin.R
import com.example.firebasekotlin.adapter.StdAdapter
import com.example.firebasekotlin.models.StudentModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var stdRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var stdList: ArrayList<StudentModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        stdRecyclerView = findViewById(R.id.rvEmp)
        stdRecyclerView.layoutManager = LinearLayoutManager(this)
        stdRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        stdList = arrayListOf<StudentModel>()

        getStudentsData()

    }

    private fun getStudentsData() {

        stdRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Students")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                stdList.clear()
                if (snapshot.exists()){
                    for (stdSnap in snapshot.children){
                        val stdData = stdSnap.getValue(StudentModel::class.java)
                        stdList.add(stdData!!)
                    }
                    val mAdapter = StdAdapter(stdList)
                    stdRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : StdAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, StudentsDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("Id", stdList[position].Id)
                            intent.putExtra("etStudentName", stdList[position].etStudentName)
                            intent.putExtra("etStudentGrade", stdList[position].etStudentGrade)
                            intent.putExtra("etFeedback", stdList[position].etFeedback)
                            startActivity(intent)
                        }

                    })

                    stdRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}