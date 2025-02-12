package ua.wied.domain.usecases

import ua.wied.domain.models.user.User
import ua.wied.domain.repository.UserStorage
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(private val userStorage: UserStorage) {
    suspend operator fun invoke(user: User) = userStorage.saveUser(user)
}

class GetUserUseCase @Inject constructor(private val userStorage: UserStorage) {
    suspend operator fun invoke(): User = userStorage.getUser()
}

class ClearUserDataUseCase @Inject constructor(private val userStorage: UserStorage) {
    suspend operator fun invoke() = userStorage.clearUserData()
}