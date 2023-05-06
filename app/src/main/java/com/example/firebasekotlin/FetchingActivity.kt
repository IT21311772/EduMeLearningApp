package com.example.firebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FetchingActivity : AppCompatActivity() {

    private lateinit var empRecyclerView : RecyclerView
    private lateinit var tvLoadingData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        empRecyclerView = findViewById(R.id.rvEmp)
        empRecyclerView.layoutManager = LinearLayoutManager(this)
        empRecyclerView
        tvLoadingData = findViewById(R.id.tvLoadingData)
    }
}