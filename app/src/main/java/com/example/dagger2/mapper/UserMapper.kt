package com.example.dagger2.mapper

import com.example.dagger2.model.UserDTOItem
import com.example.dagger2.model.actual.User

fun UserDTOItem.toUser(): User {
    return User(
        id = id, name = name, phone = phone
    )
}