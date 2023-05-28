package com.example.baseballapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StadiumDescriptionFragment : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_stadium_description, container, false)
        val s = requireActivity().intent.getParcelableExtra<StadiumModel>("stadium")

        val image = view.findViewById<ImageView>(R.id.bigStadium)
        val addressText = view.findViewById<TextView>(R.id.stadiumAddress)
        val button = view.findViewById<Button>(R.id.FindWayToStadiumButton)

        if (s!=null) {
            image.visibility = View.VISIBLE
            addressText.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
            val titleTxt = view.findViewById<TextView>(R.id.titleTxt)
            titleTxt.text = s.name
            Picasso.get()
                .load(s.imgLink)
                .resize(800, 700)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .into(image)
            val lat = s.lt
            val long = s.ln
            addressText.text = "Adres:\n${s.address}, ${s.city}"
            button.setOnClickListener { CoroutineScope(Dispatchers.IO).launch {
                val gmmIntentUri = Uri.parse("google.streetview:cbll=$lat,$long")

                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

            }
            }

        }
        return view
    }

    fun display(stadium: StadiumModel)
    {
        val imageView = requireView().findViewById<ImageView>(R.id.bigStadium)
        val addressText = requireView().findViewById<TextView>(R.id.stadiumAddress)
        val button = requireView().findViewById<Button>(R.id.FindWayToStadiumButton)
        imageView.visibility = View.VISIBLE
        addressText.visibility = View.VISIBLE
        button.visibility = View.VISIBLE
        val titleTxt = requireView().findViewById<TextView>(R.id.titleTxt)
        titleTxt.text = stadium.name

        val image = stadium.imgLink
        Picasso.get()
            .load(image)
            .resize(500, 500)
            .centerCrop()
            .placeholder(R.drawable.player)  // Placeholder image
            .error(R.drawable.error)  // Error image
            .into(imageView)
        val lat = stadium.lt
        val long = stadium.ln
        addressText.text = "Adres:\n${stadium.address}, ${stadium.city}"
        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val gmmIntentUri = Uri.parse("google.streetview:cbll=$lat,$long")

                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)

            }
        }

    }


}