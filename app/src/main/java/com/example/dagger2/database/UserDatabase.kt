package com.example.dagger2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dagger2.model.actual.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val dao: UserDao


}