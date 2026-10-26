package com.example.game.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user:User)

    @Update
    suspend fun update(user:User)

    @Delete
    suspend fun delete(user:User)

    @Query("SELECT * FROM Users WHERE userName = :name")
    suspend fun getUser(name: String): User?

    @Query("SELECT * FROM Users WHERE userName = :name")
    fun getUserStream(name: String): Flow<User?>

    @Query("SELECT * from Users ORDER BY userName ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * from Users ORDER BY bestScore DESC LIMIT 10")
    fun getRanking(): Flow<List<User>>
}