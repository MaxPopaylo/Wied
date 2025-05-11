package ua.wied.domain.repository

import ua.wied.domain.models.FlowResultList
import ua.wied.domain.models.UnitFlow
import ua.wied.domain.models.user.Role
import ua.wied.domain.models.user.User

interface UserRepository {
    suspend fun getEmployees(): FlowResultList<User>
    suspend fun createEmployee(
        login: String, password: String,
        name: String, email: String?,
        phone: String, role: Role,
        info: String?
    ): UnitFlow
    suspend fun deleteEmployee(employeeId: Int): UnitFlow
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun updateUserData(user: User)
    suspend fun clearUserData()
}