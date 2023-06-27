package com.example.myproject

import android.content.Context
import android.content.SharedPreferences

class MyPreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString("email", null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    fun saveData(email: String, password: String) {
        sharedPreferences.edit().apply {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}
