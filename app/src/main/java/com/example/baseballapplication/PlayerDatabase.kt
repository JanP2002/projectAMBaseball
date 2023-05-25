package com.example.baseballapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities=[PlayersModel::class, TeamModel::class],
    version=1
)
abstract class PlayerDatabase : RoomDatabase(){
    abstract fun playerDao(): PlayerDao
    abstract fun teamDao():TeamDao
    companion object {
        @Volatile
        private var INSTANCE: PlayerDatabase? = null

        fun getDatabase(context: Context): PlayerDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): PlayerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PlayerDatabase::class.java,
                "player_database"
            )
                .build()
        }
    }

}