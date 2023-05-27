package com.example.baseballapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class StadiumDescriptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadium_description)


        val stadium = intent.getParcelableExtra<StadiumModel>("stadium")
        if (stadium != null) {
            this.supportActionBar?.title = stadium.name
        }
    }

}