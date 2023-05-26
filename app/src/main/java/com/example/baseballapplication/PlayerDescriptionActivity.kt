package com.example.baseballapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

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