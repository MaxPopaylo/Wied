package ua.wied.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesConstants {
    const val USER_STORAGE_PREFERENCES_NAME = "user_storage_preferences"
    const val JWT_TOKEN_PREFERENCES = "jwt_token_preferences"
    const val SETTINGS_PREFERENCES = "settings_preference"

    val KEY_USER_ID = stringPreferencesKey("key_user_id")
    val KEY_USER_LOGIN = stringPreferencesKey("key_user_login")
    val KEY_USER_NAME = stringPreferencesKey("key_user_name")
    val KEY_USER_PHONE = stringPreferencesKey("key_user_phone")
    val KEY_USER_EMAIL = stringPreferencesKey("key_user_email")
    val KEY_USER_COMPANY = stringPreferencesKey("key_user_company")
    val KEY_USER_INFO = stringPreferencesKey("key_user_info")
    val KEY_USER_ROLE = stringPreferencesKey("key_user_role")

    val ACCESS_JWT_KEY = stringPreferencesKey("access_jwt")

    val LANGUAGE_KEY = stringPreferencesKey("language")
    val DARK_THEME_KEY = booleanPreferencesKey("dark_theme")
    val SHOW_AI_SUMMARY_KEY = booleanPreferencesKey("show_ai_summary")
}

internal object NetworkKeys {
    const val BASE_URL = "http://16.16.59.28:8000/"
    const val HEADER_AUTHORIZATION = "token"
    const val TOKEN_TYPE = "Bearer"
}