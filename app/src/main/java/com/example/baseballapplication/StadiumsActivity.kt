package com.example.baseballapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StadiumsActivity : AppCompatActivity(),RecyclerViewInterface {
    var stadiums = ArrayList<StadiumModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadiums)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerStadiums)

        stadiums = intent.getParcelableArrayListExtra("stadiums")!!

        val adapter = StadiumRecyclerViewAdapter(this,stadiums,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.supportActionBar?.title = "Stadiony"

    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this,StadiumDescriptionActivity::class.java )
        intent.putExtra("stadium",stadiums[position])
        startActivity(intent)
    }
}