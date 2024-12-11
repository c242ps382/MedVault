package com.dicoding.medvault.pref

import android.content.Context
import android.content.SharedPreferences
import com.dicoding.medvault.model.User
import com.google.gson.Gson

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    fun saveUser(user: User) {
        val userJson = gson.toJson(user)
        editor.putString("user", userJson)
        editor.apply()
    }

    fun getUser(): User? {
        val userJson = sharedPreferences.getString("user", null)
        return if (userJson != null) gson.fromJson(userJson, User::class.java) else null
    }

    fun clearUser() {
        editor.remove("user")
        editor.apply()
    }
}