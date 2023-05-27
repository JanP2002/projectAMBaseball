package com.example.baseballapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TeamsFragment : Fragment(), RecyclerViewInterface {

    var teamsModel = ArrayList<TeamModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTeams)



        teamsModel = requireActivity().intent.getParcelableArrayListExtra("teams")!!

        val adapter = TeamsRecyclerViewAdapter(requireContext(), teamsModel,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(requireContext(), TeamDescriptionActivity::class.java )
        intent.putExtra("team",teamsModel[position])
        startActivity(intent)
    }


}