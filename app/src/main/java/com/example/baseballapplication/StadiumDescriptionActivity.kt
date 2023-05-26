package com.example.baseballapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.squareup.picasso.Picasso

class StadiumDescriptionActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadium_description)

        val s = intent.getParcelableExtra<StadiumModel>("stadium")

        val image = findViewById<ImageView>(R.id.bigStadium)
        val addressText = findViewById<TextView>(R.id.stadiumAddress)
        val button = findViewById<Button>(R.id.FindWayToStadiumButton)

        if (s!=null) {
            Picasso.get()
                .load(s.imgLink)
                .placeholder(R.drawable.player)  // Placeholder image
                .error(R.drawable.error)  // Error image
                .fit()
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
    }

}