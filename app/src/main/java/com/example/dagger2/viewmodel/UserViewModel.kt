package com.example.dagger2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dagger2.mapper.toUser
import com.example.dagger2.model.actual.User
import com.example.dagger2.repository.UserRepository
import com.example.dagger2.utils.UserState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState.Idle)
    val state: StateFlow<UserState> get() = _state

    private val _localUsers: MutableStateFlow<List<User>> = MutableStateFlow(emptyList())
    val localUsers = _localUsers.asStateFlow()

    init {
        getRemoteUsers()
    }

    private fun getRemoteUsers() {
        viewModelScope.launch {
            _state.value = UserState.Loading
            delay(500L)
            try {
                repository.getRemoteUsers().collect {
                    _state.value = UserState.Success(it.body()?.map { item ->
                        item.toUser()
                    }!!)
                }
            } catch (e: Exception) {
                _state.value = UserState.Error(e.message!!)
            }
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.GetUser -> {
                viewModelScope.launch {
                    repository.getLocalUsers().collect {
//                        _localUsers.value = it
                        _localUsers.update {
                            it
                        }
                    }
                }
            }
            is MainEvent.AddUser -> {
                viewModelScope.launch {
                    repository.addUser(event.user)
                }
            }
            is MainEvent.UpdateUser -> {
                viewModelScope.launch {
                    repository.updateUser(event.user)
                }
            }
            is MainEvent.DeleteUser -> {
                viewModelScope.launch {
                    repository.deleteUser(event.id)
                }
            }
        }
    }
}