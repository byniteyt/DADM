package com.example.game.data

import kotlinx.coroutines.flow.Flow

public class OfflineUsersRepository(private var userDao: UserDAO): UserRepository {
    override fun getAllUsers(): Flow<List<User>> = userDao.getAllUsers()

    override suspend fun getUser(name: String): User? = userDao.getUser(name)

    override suspend fun insertUser(user: User) = userDao.insert(user)

    override suspend fun updateUser(user: User) = userDao.update(user)

    override suspend fun deleteUser(user: User) = userDao.delete(user)
}