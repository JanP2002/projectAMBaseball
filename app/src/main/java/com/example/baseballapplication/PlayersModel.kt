package com.example.baseballapplication

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
class PlayersModel (@PrimaryKey val nameAndNumber : String, val stat1: String, val stat2: String, val stat3: String, val stat4 : String, val team: String, val image : String): Parcelable