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
import ua.wied.data.UserPreferencesConstants.KEY_USER_EMAIL
import ua.wied.data.UserPreferencesConstants.KEY_USER_INFO
import ua.wied.data.UserPreferencesConstants.KEY_USER_LOGIN
import ua.wied.data.UserPreferencesConstants.KEY_USER_ROLE
import ua.wied.domain.models.user.Role

class UserStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserStoreManager {

    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = user.id.toString()
            preferences[KEY_USER_LOGIN] = user.login
            preferences[KEY_USER_NAME] = user.name
            preferences[KEY_USER_PHONE] = user.phone
            preferences[KEY_USER_EMAIL] = user.email
            preferences[KEY_USER_COMPANY] = user.company
            preferences[KEY_USER_INFO] = user.info
            preferences[KEY_USER_ROLE] = user.role.name
        }
    }

    override suspend fun getUser(): User? {
        val preferences = dataStore.data.first()

        val id = preferences[KEY_USER_ID]
        val login = preferences[KEY_USER_LOGIN]
        val name = preferences[KEY_USER_NAME]
        val phone = preferences[KEY_USER_PHONE]
        val email = preferences[KEY_USER_EMAIL]
        val company = preferences[KEY_USER_COMPANY]
        val info = preferences[KEY_USER_INFO]
        val roleString = preferences[KEY_USER_ROLE]

        return if (
            id != null && login != null && name != null &&
            phone != null && email != null && company != null &&
            info != null && roleString != null
        ) {
            val role = Role.valueOf(roleString)
            User(id.toInt(), login, name, phone, email, company, info, role)
        } else {
            null
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences.remove(KEY_USER_ID)
            preferences.remove(KEY_USER_LOGIN)
            preferences.remove(KEY_USER_NAME)
            preferences.remove(KEY_USER_PHONE)
            preferences.remove(KEY_USER_EMAIL)
            preferences.remove(KEY_USER_COMPANY)
            preferences.remove(KEY_USER_INFO)
            preferences.remove(KEY_USER_ROLE)
        }
    }

    override suspend fun updateUserData(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_LOGIN] = user.login
            preferences[KEY_USER_NAME] = user.name
            preferences[KEY_USER_PHONE] = user.phone
            preferences[KEY_USER_EMAIL] = user.email
            preferences[KEY_USER_COMPANY] = user.company
            preferences[KEY_USER_INFO] = user.info
            preferences[KEY_USER_ROLE] = user.role.name
        }
    }
}

