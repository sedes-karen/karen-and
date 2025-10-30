// data/TokenStore.kt
package com.example.karen_and.data

import android.content.SharedPreferences
import com.example.karen_and.models.UserType

class SessionStore(private val prefs: SharedPreferences) {
    companion object {
        private const val TOKEN_KEY = "auth_token"
        private const val USER_TYPE_KEY = "user_type"
    }

    fun saveSession(token: String, role: UserType) {
        prefs.edit()
            .putString(TOKEN_KEY, token)
            .putString(USER_TYPE_KEY, role.name) // "TEACHER" / "STUDENT"
            .apply()
    }


    fun getUserType(): UserType? =
        prefs.getString(USER_TYPE_KEY, null)?.let { runCatching { UserType.valueOf(it) }.getOrNull() }

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)


    fun clearSession() {
        prefs.edit().remove(TOKEN_KEY).remove(USER_TYPE_KEY).apply()
    }
}
