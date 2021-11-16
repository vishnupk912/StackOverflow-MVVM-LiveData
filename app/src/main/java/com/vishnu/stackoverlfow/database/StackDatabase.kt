package com.vishnu.stackoverlfow.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(StackEntity::class), version = 1, exportSchema = false)
abstract  class StackDatabase :RoomDatabase(){


    abstract fun getStackDao(): StackDao

    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: StackDatabase? = null

        fun getDatabase(context: Context): StackDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StackDatabase::class.java,
                    "stackoverflow_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }


}