package com.example.baseballapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.baseballapplication.R




class StadiumsFragment : Fragment(), RecyclerViewInterface {
    var stadiums = ArrayList<StadiumModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stadiums, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerStadiums)

        stadiums = requireActivity().intent.getParcelableArrayListExtra("stadiums")!!

        val adapter = StadiumRecyclerViewAdapter(requireContext(), stadiums,this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        return view
    }

    override fun onItemClick(position: Int) {


        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            val intent = Intent(requireContext(), StadiumDescriptionActivity::class.java )
            intent.putExtra("stadium",stadiums[position])
            startActivity(intent)
        }
        else
        {
            var frag = parentFragmentManager.findFragmentById(R.id.stadiumsFragmentRight) as StadiumDescriptionFragment
            frag.display(stadiums[position])
        }
    }


}