// data/TokenStore.kt
package com.example.karen_and.data

import android.content.SharedPreferences

class TokenStore(private val prefs: SharedPreferences) {
    companion object {
        private const val TOKEN_KEY = "auth_token"
    }

    fun saveToken(token: String) {
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)

    fun clearToken() {
        prefs.edit().remove(TOKEN_KEY).apply()
    }
}
