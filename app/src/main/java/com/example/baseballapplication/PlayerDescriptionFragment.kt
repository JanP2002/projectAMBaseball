package com.example.baseballapplication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PlayerDescriptionFragment : Fragment() {

    var playerName = ""
    lateinit var imageBtn : ImageButton
    var isFavorite =false
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


        val IntroText = "$name to zawodnik drużyny $team. Nosi na koszulce numer $number. "
        val BasicStats = "Zagrał w "+stats[0]+" meczach, zaliczając "+stats[1]+" podejść do bazy domowej. "
        val onBase = "W tych podejściach, zdobył "+stats[4]+" hitów, "+stats[12]+" baz darmo, oraz został uderzony narzutem "+stats[17]+" razy. "
        val runsRBI = "$name zdobył "+stats[3]+" obiegi. Po jego uderzeniach punkty zdobyło "+stats[10]+" zawodników "
        var SBCS = "Udało mu się ukraść "+stats[18]+" baz"
        SBCS += if (stats[19].toInt()>0)
            ", lecz został złapany przy próbie kradzieży "+stats[19]+" razy. "
        else
            ". "


        var opsOPSplus = "$name ma "+stats[34]+" OPS. Dla kontekstu, jest to "
        if (stats[35].toInt()>100)
            opsOPSplus+="o "+((stats[35].toInt())-100).toString()+"% lepiej niż średni ligowy zawodnik."
        else if (stats[35].toInt()<100)
            opsOPSplus+="o "+(100-(stats[35].toInt())).toString()+"% gorzej niż średni ligowy zawodnik."
        else
            opsOPSplus+="tak samo jak średni ligowy zawodnik."
        return IntroText+BasicStats+onBase+runsRBI+SBCS+opsOPSplus
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_player_description, container, false)
        val player = requireActivity().intent.getParcelableExtra<PlayersModel>("player")
        if (player != null) {

            playerName = player.nameAndNumber



            val imageView = view.findViewById<ImageView>(R.id.playerDescriptionImage)
            val imageUrl = player.image
            Picasso.get()
                .load(imageUrl)
                .resize(700,500)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .into(imageView)


            view.findViewById<TextView>(R.id.statText).text =
                createFlavourText(playerName,player.batStats,player.team)

            imageBtn = view.findViewById(R.id.playerFavButton)
            isFavorite = player.isFavorite
            if (isFavorite) {
                imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
            }
            imageBtn.setOnClickListener {

                val playerDB by lazy { PlayerDatabase.getDatabase(requireContext()).playerDao() }
                if (!isFavorite) {
                    CoroutineScope(Dispatchers.IO).launch {
                        playerDB.addToFavorites(playerName)
                    }
                    //change button appearance
                    imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
                    Toast.makeText(requireContext(),"Added to favorites", Toast.LENGTH_LONG).show()
                    isFavorite=true
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        playerDB.removeFromFavorites(playerName)
                    }
                    //change button appearance
                    imageBtn.setImageResource(R.drawable.baseline_star_24)
                    Toast.makeText(requireContext(),"Removed from favorites", Toast.LENGTH_LONG).show()
                    isFavorite=false
                }
                //Powrot do MainActivity
                val intent = Intent(requireContext(), MainActivity::class.java)
                //Usuwamy wszystkie aktywnosci ze stosu i uruchamiamy nowa kopie MainActivity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
        return view
    }

    fun display(player: PlayersModel)
    {
        val imageUrl = player.image
        val imageView = requireView().findViewById<ImageView>(R.id.playerDescriptionImage)
        Picasso.get()
            .load(imageUrl)
            .resize(300, 300)
            .centerCrop()
            .placeholder(R.drawable.player)  // Placeholder image
            .error(R.drawable.error)  // Error image
            .into(imageView)
        requireView().findViewById<TextView>(R.id.statText).text =
            createFlavourText(player.nameAndNumber, player.batStats, player.team)

        imageBtn = requireView().findViewById(R.id.playerFavButton)
//        isFavorite = player.isFavorite
        if (player.isFavorite) {
            imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
        }
        playerName = player.nameAndNumber
        imageBtn.setOnClickListener {
            val playerDB by lazy { PlayerDatabase.getDatabase(requireContext()).playerDao() }
            if (!player.isFavorite) {
                CoroutineScope(Dispatchers.IO).launch {
                    playerDB.addToFavorites(playerName)
                }
                imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
                Toast.makeText(requireContext(),"Added to favorites", Toast.LENGTH_LONG).show()
                isFavorite=true
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    playerDB.removeFromFavorites(playerName)
                }
                imageBtn.setImageResource(R.drawable.baseline_star_24)
                Toast.makeText(requireContext(),"Removed from favorites", Toast.LENGTH_LONG).show()
                isFavorite=false
            }
            //Powrot do MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            //Usuwamy wszystkie aktywnosci ze stosu i uruchamiamy nowa kopie MainActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
    }


}