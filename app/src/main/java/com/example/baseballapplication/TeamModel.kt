package com.example.baseballapplication

import android.graphics.drawable.Drawable

class TeamModel (val team: String, val img : Int, val shortName: String ) {
    var players = ArrayList<PlayersModel>()

}