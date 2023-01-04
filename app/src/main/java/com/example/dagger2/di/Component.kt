package com.example.dagger2.di

import android.provider.ContactsContract.Data
import com.example.dagger2.MainActivity
import javax.inject.Singleton

@Singleton
@dagger.Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}