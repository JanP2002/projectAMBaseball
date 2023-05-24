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

    val teamsModel = ArrayList<TeamModel>()
    var players = ArrayList<PlayersModel>()

    val playerDB by lazy { PlayerDatabase.getDatabase(this).dao() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerTeams)

        teamsModel.add(TeamModel("Silesia Rybnik", R.drawable.silesiarybnik, "SIL"))
        teamsModel.add(TeamModel("Stal Kutno", R.drawable.stalkutno, "STAL"))
        teamsModel.add(TeamModel("Centaury Warszawa", R.drawable.silesiarybnik, "CEN"))
        teamsModel.add(TeamModel("Barons Wrocław", R.drawable.stalkutno, "BAR"))
        teamsModel.add(TeamModel("Gepardy Żory", R.drawable.silesiarybnik, "GEP"))

        val adapter = TeamsRecyclerViewAdapter(this,teamsModel,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        this.supportActionBar?.title = "Drużyny"


        CoroutineScope(Dispatchers.IO).launch {
            for (i in 0..4) {
                teamsModel[i].players = playerDB.getTeamPlayers(teamsModel[i].shortName) as ArrayList<PlayersModel>
                teamsModel[i].players.sortBy { -it.stat4 }
            }
        }
//        Toast.makeText(this,players.size.toString(),Toast.LENGTH_LONG).show()
    }

    override fun onItemClick(position: Int) {
//        val shortName = teamsModel[position].shortName
//        val playerDB by lazy { PlayerDatabase.getDatabase(this).dao() }
//        var p = ArrayList<PlayersModel>()
//        CoroutineScope(Dispatchers.IO).launch {
//            p = playerDB.getTeamPlayers(shortName) as ArrayList<PlayersModel>
//            p.sortBy { -it.stat4 }
//        }
        val intent = Intent(this, PlayersActivity::class.java)
        intent.putParcelableArrayListExtra("players",teamsModel[position].players)
        startActivity(intent)
    }
}