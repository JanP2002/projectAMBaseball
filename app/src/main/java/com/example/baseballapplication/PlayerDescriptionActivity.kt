package com.example.baseballapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PlayerDescriptionActivity : AppCompatActivity() {
    var playerName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_description)

        val player = intent.getParcelableExtra<PlayersModel>("player")
        if (player != null) {

            playerName = player.nameAndNumber
            this.supportActionBar?.title = playerName
        }

    }

}
