package com.example.game.ui

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.game.data.OfflineUsersRepository
import com.example.game.data.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val userRepository: OfflineUsersRepository,
    userId: Int
    ): ViewModel() {
    val user: StateFlow<User?> = userRepository.getUser(userId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            null
        )

    fun updateUser(name: String, points: Int){
        val currentUser = user.value?: return
        viewModelScope.launch {
            if (currentUser.bestScore < points)
                userRepository.updateUser(currentUser.copy(userName = name, points = points, bestScore = points.toDouble()))
            else
                userRepository.updateUser(currentUser.copy(userName = name, points = points))
        }
    }

    fun deleteUser(onDeleted:()-> Unit){
        val currentUser = user.value?: return
        viewModelScope.launch {
            userRepository.deleteUser(currentUser)
        }
    }
}

class UserDetailViewModelFactory(
    private val userRepository: OfflineUsersRepository,
    val userId: Int
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModel(userRepository, userId) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}