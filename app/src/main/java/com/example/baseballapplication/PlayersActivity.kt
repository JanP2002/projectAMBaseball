package com.example.baseballapplication

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class PlayersActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        when(intent.getStringExtra("mode")) {
            MainActivity.teamMode -> {
                val teamName = intent.getStringExtra("teamName")
                this.supportActionBar?.title = teamName
            }
            MainActivity.favMode -> this.supportActionBar?.title = "Ulubieni Zawodnicy"
            else -> this.supportActionBar?.title = "Zawodnicy"
        }






    }



}