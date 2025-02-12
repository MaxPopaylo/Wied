package ua.wied.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ua.wied.data.UserPreferencesConstants.KEY_USER_COMPANY
import ua.wied.data.UserPreferencesConstants.KEY_USER_ID
import ua.wied.data.UserPreferencesConstants.KEY_USER_NAME
import ua.wied.data.UserPreferencesConstants.KEY_USER_PHONE
import ua.wied.domain.models.user.User
import ua.wied.domain.repository.UserStoreManager
import javax.inject.Inject

import kotlinx.coroutines.flow.first

class UserStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserStoreManager {

    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = user.id
            preferences[KEY_USER_NAME] = user.name
            preferences[KEY_USER_PHONE] = user.phone
            preferences[KEY_USER_COMPANY] = user.company
        }
    }

    override suspend fun getUser(): User? {
        val preferences = dataStore.data.first()

        val id = preferences[KEY_USER_ID]
        val name = preferences[KEY_USER_NAME]
        val phone = preferences[KEY_USER_PHONE]
        val company = preferences[KEY_USER_COMPANY]

        return if (id != null && name != null && phone != null && company != null) {
            User(id, name, phone, company)
        } else {
            null
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USER_NAME)
            preferences.remove(KEY_USER_PHONE)
            preferences.remove(KEY_USER_COMPANY)
        }
    }

    override suspend fun updateUserData(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_NAME] = user.name
            preferences[KEY_USER_PHONE] = user.phone
            preferences[KEY_USER_COMPANY] = user.company
        }
    }
}

