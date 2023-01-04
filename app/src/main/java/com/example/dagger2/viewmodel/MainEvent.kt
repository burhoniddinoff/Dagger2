package com.example.dagger2.viewmodel

import com.example.dagger2.model.actual.User

sealed class MainEvent {
    data class AddUser(val user: User): MainEvent()
    data class UpdateUser(val user: User): MainEvent()
    data class DeleteUser(val id: Long): MainEvent()
    object GetUser: MainEvent()

}