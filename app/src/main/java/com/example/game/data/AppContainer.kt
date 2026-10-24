package com.example.inventory.data

import android.content.Context
import com.example.game.data.OfflineUsersRepository
import com.example.game.data.UserDatabase
import com.example.game.data.UserRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val usersRepository: UserRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineItemsRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val usersRepository: UserRepository by lazy {
        OfflineUsersRepository(UserDatabase.getDataBase(context).userDao())
    }
}
