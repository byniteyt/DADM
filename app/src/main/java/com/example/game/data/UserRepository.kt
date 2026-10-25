package com.example.game.data

import androidx.room.Delete
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers():Flow<List<User>>
    suspend fun getUser(name: String): User?
    suspend fun insertUser(user:User)
    suspend fun updateUser(user:User)
    suspend fun deleteUser(user:User)
}