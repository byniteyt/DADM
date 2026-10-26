package com.example.game.data

import androidx.room.Query
import kotlinx.coroutines.flow.Flow

public class OfflineUsersRepository(private var userDao: UserDAO): UserRepository {
    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override suspend fun getUser(name: String): User? = userDao.getUser(name)

    override fun getUserStream(name: String): Flow<User?> = userDao.getUserStream(name)

    @Query("SELECT * from Users ORDER BY bestScore DESC LIMIT 10")
    override fun getRanking(): Flow<List<User>> = userDao.getRanking()

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)
}