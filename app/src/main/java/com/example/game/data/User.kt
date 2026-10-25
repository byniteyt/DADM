package com.example.game.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Users")
data class User (
    @PrimaryKey
    val userName: String,
    val bestScore: Double,
    val points: Int
)