package com.example.baseballapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class PlayersModel (val nameAndNumber : String, val stat1: String, val stat2: String, val stat3: String, val stat4 : String, val image : Int): Parcelable