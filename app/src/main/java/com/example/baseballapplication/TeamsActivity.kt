package com.example.baseballapplication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsActivity : AppCompatActivity(), RecyclerViewInterface {

    var teamsModel = ArrayList<TeamModel>()


//    val playerDB by lazy { PlayerDatabase.getDatabase(this).playerDao() }
//    val teamsDB by lazy {PlayerDatabase.getDatabase(this).teamDao()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTeams)



        teamsModel = intent.getParcelableArrayListExtra("teams")!!

        val adapter = TeamsRecyclerViewAdapter(this,teamsModel,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.supportActionBar?.title = "Drużyny"

    }

    override fun onItemClick(position: Int) {

//        val intent = Intent(this, PlayersActivity::class.java)
//        CoroutineScope(Dispatchers.IO).launch {
//            val teamPlayers = playerDB.getTeamPlayers(teamsModel[position].shortName) as ArrayList<PlayersModel>
//            intent.putParcelableArrayListExtra("players",teamPlayers)
//            startActivity(intent)
//        }
        val intent = Intent(this,TeamDescriptionActivity::class.java )
        intent.putExtra("team",teamsModel[position])
        startActivity(intent)


    }
}