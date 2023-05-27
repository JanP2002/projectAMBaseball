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
            Picasso.get()
                .load(t.imgLink)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .fit()
                .into(image)
            val w = t.wins
            val l = t.losses
            view.findViewById<TextView>(R.id.teamWinLossText).text = "$w wygranych, $l przegranych"
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
            }

        }
        return view
    }


}