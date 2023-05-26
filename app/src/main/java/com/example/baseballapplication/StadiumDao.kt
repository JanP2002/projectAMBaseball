package com.example.baseballapplication

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface StadiumDao {
    @Upsert
    suspend fun insertStadium(stadium: StadiumModel)

    @Query("SELECT * from StadiumModel")
    fun getAllStadiums() : List<StadiumModel>
}