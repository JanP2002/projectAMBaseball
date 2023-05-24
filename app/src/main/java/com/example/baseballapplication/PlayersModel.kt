package com.example.baseballapplication

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
class PlayersModel (@PrimaryKey val nameAndNumber : String, val stat1: Int, val stat2: Int, val stat3: Int, val stat4 : Double, val team: String, val image : String): Parcelable