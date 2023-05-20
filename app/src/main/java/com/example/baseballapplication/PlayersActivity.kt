package com.example.baseballapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PlayersActivity : AppCompatActivity() {
    var playersModel = ArrayList<PlayersModel>()
    var playerImage = R.drawable.player
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerPlayers)

        val p : ArrayList<PlayersModel> = intent.getParcelableArrayListExtra("players")!!
        playersModel = p
        val adapter = PlayersRecyclerViewAdapter(this, playersModel)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        //  setUpPlayerModels()
    }

//    private fun setUpPlayerModels() {
//
//        //tu nazwy z bazy danych??
//        val playerName = getString(R.string.defName)
//        val stat1 = getString(R.string.stats)
//        val stat2 = getString(R.string.stats2)
//        val stat3 = getString(R.string.stats3)
//        val stat4 = getString(R.string.stats4)
//        for (i in 0..99) {
//            playersModel.add(PlayersModel(playerName, stat1, stat2, stat3, stat4, playerImage))
//        }
//    }
}