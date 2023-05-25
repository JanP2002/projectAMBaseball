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
}