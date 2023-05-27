package com.example.baseballapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text


class TeamDescriptionFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_team_description, container, false)
        val t = requireActivity().intent.getParcelableExtra<TeamModel>("team")


        val image = view.findViewById<ImageView>(R.id.teamLogoBig)
        val seePlayersButton = view.findViewById<Button>(R.id.SeePlayersButton)
        val imageBtn = view.findViewById<ImageButton>(R.id.teamFavButton)
        if (t != null) {
            val loseWonText = view.findViewById<TextView>(R.id.teamWinLossText)
            val favPrompt = view.findViewById<TextView>(R.id.FavPrompt)
            image.visibility = View.VISIBLE
            seePlayersButton.visibility = View.VISIBLE
            imageBtn.visibility = View.VISIBLE
            loseWonText.visibility = View.VISIBLE
            favPrompt.visibility = View.VISIBLE
            Picasso.get()
                .load(t.imgLink)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .fit()
                .into(image)
            val w = t.wins
            val l = t.losses
            loseWonText.text = "$w wygranych, $l przegranych w sezonie 2023"
            seePlayersButton.setOnClickListener {
                val intent = Intent(requireContext(), PlayersActivity::class.java)
                val playerDB by lazy { PlayerDatabase.getDatabase(requireContext()).playerDao() }
                CoroutineScope(Dispatchers.IO).launch {
                    val teamPlayers = playerDB.getTeamPlayers(t.shortName) as ArrayList<PlayersModel>
                    intent.putParcelableArrayListExtra("players",teamPlayers)
                    startActivity(intent)
                }
            }

            if (t.isFavorite)
                imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)

            imageBtn.setOnClickListener {
                val teamDB by lazy { PlayerDatabase.getDatabase(requireContext()).teamDao() }
                if (!t.isFavorite) {
                    CoroutineScope(Dispatchers.IO).launch {
                        teamDB.addToFavorites(t.shortName)
                    }
                    imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
                    Toast.makeText(requireContext(),"Added to favorites", Toast.LENGTH_LONG).show()

                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        teamDB.removeFromFavorites(t.shortName)
                    }
                    imageBtn.setImageResource(R.drawable.baseline_star_24)
                    Toast.makeText(requireContext(),"Removed from favorites", Toast.LENGTH_LONG).show()

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

    fun display(team: TeamModel)
    {
        val image = requireView().findViewById<ImageView>(R.id.teamLogoBig)
        val seePlayersButton = requireView().findViewById<Button>(R.id.SeePlayersButton)
        val imageBtn = requireView().findViewById<ImageButton>(R.id.teamFavButton)
        val loseWonText = requireView().findViewById<TextView>(R.id.teamWinLossText)
        val favPrompt = requireView().findViewById<TextView>(R.id.FavPrompt)

        loseWonText.visibility = View.VISIBLE
        favPrompt.visibility = View.VISIBLE
        image.visibility = View.VISIBLE
        seePlayersButton.visibility = View.VISIBLE
        imageBtn.visibility = View.VISIBLE
        Picasso.get()
            .load(team.imgLink)
            .resize(400, 400)
            .centerCrop()
            .placeholder(R.drawable.player)  // Placeholder image
            .error(R.drawable.error)  // Error image
            .into(image)
        val w = team.wins
        val l = team.losses
        requireView().findViewById<TextView>(R.id.teamWinLossText).text = "$w wygranych, $l przegranych"
        seePlayersButton.setOnClickListener {
            val intent = Intent(requireContext(), PlayersActivity::class.java)
            val playerDB by lazy { PlayerDatabase.getDatabase(requireContext()).playerDao() }
            CoroutineScope(Dispatchers.IO).launch {
                val teamPlayers = playerDB.getTeamPlayers(team.shortName) as ArrayList<PlayersModel>
                intent.putParcelableArrayListExtra("players",teamPlayers)
                startActivity(intent)
            }
        }
        if (team.isFavorite)
            imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
        else
            imageBtn.setImageResource(R.drawable.baseline_star_24)

        imageBtn.setOnClickListener {
            val teamDB by lazy { PlayerDatabase.getDatabase(requireContext()).teamDao() }
            if (!team.isFavorite) {
                CoroutineScope(Dispatchers.IO).launch {
                    teamDB.addToFavorites(team.shortName)
                }
                imageBtn.setImageResource(R.drawable.baseline_star_24_yellow)
                Toast.makeText(requireContext(),"Added to favorites", Toast.LENGTH_LONG).show()

            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    teamDB.removeFromFavorites(team.shortName)
                }
                imageBtn.setImageResource(R.drawable.baseline_star_24)
                Toast.makeText(requireContext(),"Removed from favorites", Toast.LENGTH_LONG).show()

            }
            //Powrot do MainActivity
            val intent = Intent(requireContext(), MainActivity::class.java)
            //Usuwamy wszystkie aktywnosci ze stosu i uruchamiamy nowa kopie MainActivity
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }


    }


}