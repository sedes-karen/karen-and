package com.example.karen_and.data

import android.content.SharedPreferences
import com.example.karen_and.models.UserModel
import com.example.karen_and.models.UserType

class SessionStore(private val prefs: SharedPreferences) {
    companion object {
        private const val TOKEN_KEY = "auth_token"
        private const val USER_TYPE_KEY = "user_type"
        private const val USER_NAME_KEY = "user_name"
        private const val USER_LASTNAME_KEY = "user_lastname"
        private const val USER_EMAIL_KEY = "user_email"

    }

    fun saveSession(token: String, user: UserModel) {
        prefs.edit()
            .putString(TOKEN_KEY, token)
            .putString(USER_TYPE_KEY, user.typeUser.name)
            .putString(USER_NAME_KEY, user.name)
            .putString(USER_EMAIL_KEY, user.email)
            .putString(USER_LASTNAME_KEY, user.lastname)
            .apply()
    }

    fun getUserType(): UserType? =
        prefs.getString(USER_TYPE_KEY, null)?.let {
            runCatching { UserType.valueOf(it) }.getOrNull()
        }

    fun getToken(): String? = prefs.getString(TOKEN_KEY, null)

    fun getUserName(): String? = prefs.getString(USER_NAME_KEY, null)
    fun getUserLastname(): String? = prefs.getString(USER_LASTNAME_KEY, null)
    fun getUserEmail(): String? = prefs.getString(USER_EMAIL_KEY, null)

    fun clearSession() {
        prefs.edit()
            .remove(TOKEN_KEY)
            .remove(USER_TYPE_KEY)
            .remove(USER_NAME_KEY)
            .remove(USER_LASTNAME_KEY)
            .apply()
    }
}
