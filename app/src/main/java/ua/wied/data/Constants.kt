package ua.wied.data

import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesConstants {
    const val USER_STORAGE_PREFERENCES_NAME = "user_storage_preferences"
    const val JWT_TOKEN_PREFERENCES = "jwt_token_preferences"

    val KEY_USER_ID = stringPreferencesKey("key_user_id")
    val KEY_USER_NAME = stringPreferencesKey("key_user_name")
    val KEY_USER_PHONE = stringPreferencesKey("key_user_phone")
    val KEY_USER_COMPANY = stringPreferencesKey("key_user_company")

    val ACCESS_JWT_KEY = stringPreferencesKey("access_jwt")
}
