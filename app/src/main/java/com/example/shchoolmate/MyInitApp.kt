package com.example.shchoolmate

import android.app.Application

class MyInitApp : Application() {
    val database: AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}