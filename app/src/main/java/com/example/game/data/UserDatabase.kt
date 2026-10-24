package com.example.game.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class],version = 1,exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object{
        @Volatile private var Instance: UserDatabase? = null

        fun getDataBase(context: Context): UserDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(
                    context, UserDatabase::class.java,
                    "user_database"
                ).build().also { Instance = it }
            }
        }
    }
}