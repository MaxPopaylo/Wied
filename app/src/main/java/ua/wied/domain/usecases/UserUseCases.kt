package ua.wied.domain.usecases

import ua.wied.domain.models.user.User
import ua.wied.domain.repository.UserStoreManager
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val userStorage: UserStoreManager) {
    suspend operator fun invoke(user: User) = userStorage.saveUser(user)
}

class GetUserUseCase @Inject constructor(private val userStorage: UserStoreManager) {
    suspend operator fun invoke(): User? = userStorage.getUser()
}

class UpdateUserDataUseCase @Inject constructor(private val userStorage: UserStoreManager) {
    suspend operator fun invoke(user: User) = userStorage.updateUserData(user)
}

class ClearUserDataUseCase @Inject constructor(private val userStorage: UserStoreManager) {
    suspend operator fun invoke() = userStorage.clearUserData()
}