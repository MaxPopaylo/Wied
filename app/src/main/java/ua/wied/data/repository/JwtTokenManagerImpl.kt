package ua.wied.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ua.wied.data.UserPreferencesConstants.ACCESS_JWT_KEY
import ua.wied.domain.repository.JwtTokenManager
import javax.inject.Inject

class JwtTokenManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : JwtTokenManager {

    override suspend fun saveAccessJwt(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_JWT_KEY] = token
        }
    }

    override suspend fun getAccessJwt(): String? {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_JWT_KEY]
        }.first()
    }

    override suspend fun clearAllTokens() {
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_JWT_KEY)
        }
    }
}