package com.example.baseballapplication

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity
class StadiumModel (@PrimaryKey var name: String, var lt : String, var ln : String, var imgLink : String, var city: String, var address: String ) : Parcelable