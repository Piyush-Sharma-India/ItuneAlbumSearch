package com.android.example.myapplication

import android.app.Application
import com.android.example.myapplication.di.AppComponent
import com.android.example.myapplication.di.DaggerAppComponent

class AlbumApplication : Application() {
    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    fun initializeComponent(): AppComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }
}