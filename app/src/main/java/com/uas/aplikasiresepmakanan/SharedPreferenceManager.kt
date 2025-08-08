package com.uas.aplikasiresepmakanan

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {
    val prefs: SharedPreferences =
        context.getSharedPreferences("UserData", Context.MODE_PRIVATE)

    fun saveUser(username: String, email: String, password: String) {
        prefs.edit().apply {
            putString("username", username)
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    fun isUserExist(username: String, password: String): Boolean {
        val storedUsername = prefs.getString("username", null)
        val storedPassword = prefs.getString("password", null)
        return username == storedUsername && password == storedPassword
    }

    fun getUsername(): String? {
        return prefs.getString("username", null)
    }

    fun clearUserData() {
        prefs.edit().clear().apply()
    }
}
