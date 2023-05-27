package com.example.baseballapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StadiumsActivity : AppCompatActivity() {
    var stadiums = ArrayList<StadiumModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadiums)

        this.supportActionBar?.title = "Stadiony"

    }

}