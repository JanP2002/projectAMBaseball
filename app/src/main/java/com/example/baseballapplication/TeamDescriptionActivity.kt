package com.example.baseballapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.squareup.picasso.Picasso

class TeamDescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_description)

        val t = intent.getParcelableExtra<TeamModel>("team")


        val image = findViewById<ImageView>(R.id.teamLogoBig)
        val seePlayersButton = findViewById<Button>(R.id.SeePlayersButton)
        val imageBtn = findViewById<ImageButton>(R.id.teamFavButton)
        if (t != null) {
            Picasso.get()
                .load(t.imgLink)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .fit()
                .into(image)
            val w = t.wins
            val l = t.losses
            findViewById<TextView>(R.id.teamWinLossText).text = "$w wygranych, $l przegranych"
            seePlayersButton.setOnClickListener {
                val intent = Intent(this, PlayersActivity::class.java)
                val playerDB by lazy { PlayerDatabase.getDatabase(this).playerDao() }
                CoroutineScope(Dispatchers.IO).launch {
                    val teamPlayers = playerDB.getTeamPlayers(t.shortName) as ArrayList<PlayersModel>
                    intent.putParcelableArrayListExtra("players",teamPlayers)
                    startActivity(intent)
                }
            }

            if (t.isFavorite)
                imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)

            imageBtn.setOnClickListener {
                val teamDB by lazy { PlayerDatabase.getDatabase(this).teamDao() }
                if (!t.isFavorite) {
                    CoroutineScope(Dispatchers.IO).launch {
                        teamDB.addToFavorites(t.shortName)
                    }
                    imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
                    Toast.makeText(this,"Added to favorites", Toast.LENGTH_LONG).show()

                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        teamDB.removeFromFavorites(t.shortName)
                    }
                    imageBtn.setImageResource(R.drawable.baseline_star_24)
                    Toast.makeText(this,"Removed from favorites", Toast.LENGTH_LONG).show()

                }
            }

        }


    }
}