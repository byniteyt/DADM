package com.example.game.data

import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAllUsers():Flow<List<User>>
    suspend fun getUser(name: String): User?
    fun getUserStream(name: String): Flow<User?>
    suspend fun insertUser(user:User)
    suspend fun updateUser(user:User)
    suspend fun deleteUser(user:User)
}