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

        var players = ArrayList<PlayersModel>()
        val playerDB by lazy { PlayerDatabase.getDatabase(this).dao() }
        CoroutineScope(Dispatchers.IO).launch {
            players = playerDB.getAllPlayers() as ArrayList<PlayersModel>
            players.sortBy { -it.stat4 }
        }
//        Toast.makeText(this,players.size.toString(), Toast.LENGTH_LONG).show()
        //  setUpPlayerModels()
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, PlayerDescriptionActivity::class.java)

//        intent.putParcelableArrayListExtra("players",players)
        startActivity(intent);
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