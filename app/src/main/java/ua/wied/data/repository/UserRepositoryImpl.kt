package ua.wied.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import ua.wied.data.UserPreferencesConstants.KEY_USER_COMPANY
import ua.wied.data.UserPreferencesConstants.KEY_USER_ID
import ua.wied.data.UserPreferencesConstants.KEY_USER_NAME
import ua.wied.data.UserPreferencesConstants.KEY_USER_PHONE
import ua.wied.domain.models.user.User
import ua.wied.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import ua.wied.data.UserPreferencesConstants.KEY_USER_EMAIL
import ua.wied.data.UserPreferencesConstants.KEY_USER_INFO
import ua.wied.data.UserPreferencesConstants.KEY_USER_LOGIN
import ua.wied.data.UserPreferencesConstants.KEY_USER_ROLE
import ua.wied.data.datasource.network.api.UserApi
import ua.wied.data.datasource.network.dto.users.CreateEmployeeDto
import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.user.Role

class UserRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val api: UserApi
) : BaseRepository(), UserRepository {

    override suspend fun getEmployees(): FlowResultList<User> =
        handleGETApiCall (
            apiCall = {
                api.getEmployees()
            },
            transform = { it.data }
        )

    override suspend fun createEmployee(
        login: String, password: String,
        name: String, email: String?,
        phone: String, role: Role,
        info: String?
    ): UnitFlow = handlePOSTApiCall (
        apiCall = {
            api.createEmployee(
                dto = CreateEmployeeDto(
                    login = login,
                    password = password,
                    name = name,
                    email = email,
                    phone = phone,
                    role = role,
                    info = info
                ),
            )
        }
    )

    override suspend fun deleteEmployee(employeeId: Int): UnitFlow =
        handleDELETEApiCall (
            apiCall = { api.deleteEmployee(employeeId) }
        )

    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[KEY_USER_ID] = user.id.toString()
            preferences[KEY_USER_LOGIN] = user.login
            preferences[KEY_USER_NAME] = user.name
            preferences[KEY_USER_PHONE] = user.phone
            preferences[KEY_USER_COMPANY] = user.company
            preferences[KEY_USER_ROLE] = user.role.name

            user.email?.let { preferences[KEY_USER_EMAIL] = it }
            user.info?.let { preferences[KEY_USER_INFO] = it }
        }
    }

    override suspend fun getUser(): User? {
        val preferences = dataStore.data.first()

        val id = preferences[KEY_USER_ID]
        val login = preferences[KEY_USER_LOGIN]
        val name = preferences[KEY_USER_NAME]
        val phone = preferences[KEY_USER_PHONE]
        val company = preferences[KEY_USER_COMPANY]
        val roleString = preferences[KEY_USER_ROLE]
        val email = preferences[KEY_USER_EMAIL]
        val info = preferences[KEY_USER_INFO]

        return if (
            id != null && login != null && name != null &&
            phone != null && company != null && roleString != null
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
            preferences[KEY_USER_COMPANY] = user.company
            preferences[KEY_USER_ROLE] = user.role.name

            user.email?.let { preferences[KEY_USER_EMAIL] = it }
            user.info?.let { preferences[KEY_USER_INFO] = it }
        }
    }
}

