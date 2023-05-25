package com.example.baseballapplication


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class TeamModel (@PrimaryKey val team: String, val wins : Int, val losses : Int, val imgLink : String, val shortName: String , val isFavorite: Boolean) : Parcelable