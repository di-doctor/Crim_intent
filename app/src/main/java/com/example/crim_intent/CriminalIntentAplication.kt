package com.example.crim_intent

import android.app.Application

class CriminalIntentAplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}