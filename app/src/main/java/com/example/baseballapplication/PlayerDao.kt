package com.example.baseballapplication

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PlayerDao {
    @Upsert
    suspend fun insertPlayer(player: PlayersModel)

    @Query("SELECT * from PlayersModel")
    fun getAllPlayers() : List<PlayersModel>

}