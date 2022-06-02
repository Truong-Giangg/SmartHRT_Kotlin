package com.first_java_app.k_login_signup

import android.app.Application

class MyApp : Application() {
    lateinit var prefs : MySharedPreferences
    override fun onCreate() {
        super.onCreate()
        prefs = MySharedPreferences()
        prefs.init(this)
    }
}