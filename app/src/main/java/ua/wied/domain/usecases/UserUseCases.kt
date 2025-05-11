package ua.wied.domain.usecases

import ua.wied.domain.models.user.Role
import ua.wied.domain.models.user.User
import ua.wied.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val userStorage: UserRepository) {
    suspend operator fun invoke(user: User) = userStorage.saveUser(user)
}

class GetUserUseCase @Inject constructor(private val userStorage: UserRepository) {
    suspend operator fun invoke(): User? = userStorage.getUser()
}

class UpdateUserDataUseCase @Inject constructor(private val userStorage: UserRepository) {
    suspend operator fun invoke(user: User) = userStorage.updateUserData(user)
}

class ClearUserDataUseCase @Inject constructor(private val userStorage: UserRepository) {
    suspend operator fun invoke() = userStorage.clearUserData()
}

class GetEmployeesUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke()= userRepository.getEmployees()
}

class CreateEmployeeUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        login: String, password: String,
        name: String, email: String?,
        phone: String, role: Role,
        info: String?
    ) = userRepository.createEmployee(
        login = login,
        password = password,
        name = name,
        email = email,
        phone = phone,
        role = role,
        info = info
    )
}

class DeleteEmployeeUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(employeeId: Int) = userRepository.deleteEmployee(employeeId)
}