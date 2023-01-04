package com.example.dagger2

import android.app.Application
import com.example.dagger2.di.AppComponent
import com.example.dagger2.di.DaggerAppComponent
import com.example.dagger2.di.DatabaseModule

class MyApp : Application() {
    companion object {
        lateinit var component: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}