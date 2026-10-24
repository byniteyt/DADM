package com.example.game.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.game.data.OfflineUsersRepository
import com.example.game.data.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: OfflineUsersRepository): ViewModel() {
    val users = userRepository.getAllUsers().stateIn(viewModelScope,
        SharingStarted.WhileSubscribed(), emptyList())
    fun addUser(user: User){
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }
}

class UserViewModelFactory(private val userRepository: OfflineUsersRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }
}