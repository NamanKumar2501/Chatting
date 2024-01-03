package com.example.chatting.SharedPrefrence

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {
    val sharedPreferences: SharedPreferences
    val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences("Naman", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun name(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun phoneNumber(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun Email(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun Password(key: String, value: String) {
        editor.putString(key, value)
            .apply()
    }

    fun checkLogin(key: String, value: Boolean) {
        editor.putBoolean(key, value)
            .apply()
    }

    fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clear() {
        editor.clear()
            .apply()
    }

}
