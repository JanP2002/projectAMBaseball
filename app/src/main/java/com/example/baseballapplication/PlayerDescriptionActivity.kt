package com.example.baseballapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class PlayerDescriptionActivity : AppCompatActivity() {
    var playerName = ""
    fun createFlavourText(nameString : String, statString : String, shortenedTeam : String) : String {
        val firstSpaceInName = nameString.indexOf(' ',0)
        val name = nameString.takeLast(nameString.length-firstSpaceInName)
        var team = ""
        val number = nameString.take(firstSpaceInName).replace("#","")
        team = if (shortenedTeam=="SIL")
            "Silesia Rybnik"
        else if (shortenedTeam=="STAL")
            "Stal Kutno"
        else if (shortenedTeam=="GEP")
            "Gepardy Żory"
        else if (shortenedTeam=="CEN")
            "Centaury Warszawa"
        else if (shortenedTeam=="BAR")
            "Barons Wrocław"
        else
            "nieznanej"

        val stats = statString.split(" ")


        val IntroText = "$name to zawodnik drużyny $team. Nosi na koszulce numer $number.\n"
        val BasicStats = "Zagrał w "+stats[0]+" meczach, zaliczając "+stats[1]+" podejść do bazy domowej.\n"
        val onBase = "W tych podejściach, zdobył "+stats[4]+" hitów, "+stats[12]+" baz darmo, oraz został uderzony narzutem "+stats[17]+" razy.\n"
        val runsRBI = "$name zdobył "+stats[3]+" obiegi. Po jego uderzeniach punkty zdobyło "+stats[10]+" zawodników\n"
        var SBCS = "Udało mu się ukraść "+stats[18]+" baz"
        SBCS += if (stats[19].toInt()>0)
            ", lecz został złapany przy próbie kradzieży "+stats[19]+" razy.\n"
        else
            ".\n"


        var opsOPSplus = "$name ma "+stats[34]+" OPS. Dla kontekstu, jest to "
        if (stats[35].toInt()>100)
            opsOPSplus+="o "+((stats[35].toInt())-100).toString()+" lepiej niż średni ligowy zawodnik."
        else if (stats[35].toInt()<100)
            opsOPSplus+="o "+(100-(stats[35].toInt())).toString()+" gorzej niż średni ligowy zawodnik."
        else
            opsOPSplus+="tak samo jak średni ligowy zawodnik."
        return IntroText+BasicStats+onBase+runsRBI+SBCS+opsOPSplus
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_description)

        val player = intent.getParcelableExtra<PlayersModel>("player")
        if (player != null) {

            playerName = player.nameAndNumber


            val imageView = findViewById<ImageView>(R.id.playerDescriptionImage)
            val imageUrl = player.image
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .fit()
                .into(imageView)

            this.supportActionBar?.title = playerName

            findViewById<TextView>(R.id.statText).text = createFlavourText(playerName,player.batStats,player.team)
        }

    }
}