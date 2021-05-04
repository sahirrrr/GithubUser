package com.dicoding.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserFavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // creating database
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "favorite_db")
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getUserFavoriteDao(): UserFavoriteDao
}