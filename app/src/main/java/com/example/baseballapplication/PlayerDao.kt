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

    @Query("SELECT * FROM PlayersModel WHERE team = :shortTeam")
    fun getTeamPlayers(shortTeam : String) : List<PlayersModel>

    @Query("UPDATE PlayersModel SET isFavorite = 1 WHERE nameAndNumber = :name")
    fun addToFavorites(name : String)

    @Query("UPDATE PlayersModel SET isFavorite = 0 WHERE nameAndNumber = :name")
    fun removeFromFavorites(name : String)

    @Query("SELECT * FROM PlayersModel WHERE isFavorite = 1")
    fun getFavoritePlayers() : List<PlayersModel>
    @Query("SELECT isFavorite FROM PlayersModel WHERE nameAndNumber = :name")
    fun checkIfFavorite(name : String) : Boolean
}