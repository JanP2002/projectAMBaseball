package com.example.baseballapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PlayersFragment : Fragment(), RecyclerViewInterface {
    var playersModel = ArrayList<PlayersModel>()
    var playerImage = R.drawable.player

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_players, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerPlayers)
        val p : ArrayList<PlayersModel> = requireActivity().intent.getParcelableArrayListExtra("players")!!
        playersModel = p
        val adapter = PlayersRecyclerViewAdapter(requireContext(), playersModel, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        return view
    }

    override fun onItemClick(position: Int) {


        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            val intent = Intent(requireActivity(), PlayerDescriptionActivity::class.java)
            intent.putExtra("player",playersModel[position])
//          intent.putParcelableArrayListExtra("players",players)
            startActivity(intent);
        }
        else
        {
            var frag = parentFragmentManager.findFragmentById(R.id.playersFragmentRight) as PlayerDescriptionFragment
            frag.display(playersModel[position])
        }
    }

}