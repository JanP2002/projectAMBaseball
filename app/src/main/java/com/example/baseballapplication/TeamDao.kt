package com.example.baseballapplication

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface TeamDao {
    @Upsert
    suspend fun insertTeam(team: TeamModel)

    @Query("SELECT * from TeamModel")
    fun getAllTeams() : List<TeamModel>

    @Query("UPDATE TeamModel SET isFavorite = 1 WHERE shortName = :name")
    fun addToFavorites(name : String)

    @Query("UPDATE TeamModel SET isFavorite = 0 WHERE shortName = :name")
    fun removeFromFavorites(name : String)

    @Query("SELECT * FROM TeamModel WHERE isFavorite = 1")
    fun getFavoriteTeams() : List<TeamModel>
    @Query("SELECT isFavorite FROM TeamModel WHERE shortName = :name")
    fun checkIfFavorite(name : String) : Boolean
}