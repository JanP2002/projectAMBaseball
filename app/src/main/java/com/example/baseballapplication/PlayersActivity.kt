package com.example.baseballapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayersActivity : AppCompatActivity(), RecyclerViewInterface {
    var playersModel = ArrayList<PlayersModel>()
    var playerImage = R.drawable.player
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPlayers)

        val p : ArrayList<PlayersModel> = intent.getParcelableArrayListExtra("players")!!
        playersModel = p
        val adapter = PlayersRecyclerViewAdapter(this, playersModel, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.supportActionBar?.title = "Zawodnicy"



    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, PlayerDescriptionActivity::class.java)
        intent.putExtra("player",playersModel[position])
//        intent.putParcelableArrayListExtra("players",players)
        startActivity(intent);
    }


}