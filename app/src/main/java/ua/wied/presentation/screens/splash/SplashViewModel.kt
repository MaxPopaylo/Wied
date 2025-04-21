package ua.wied.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ua.wied.domain.models.user.Role
import ua.wied.domain.usecases.GetAccessJwtUseCase
import ua.wied.domain.usecases.GetUserUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessJwt: GetAccessJwtUseCase,
    private val getUser: GetUserUseCase
) : ViewModel() {

    private val _isUserAuthorized = MutableSharedFlow<Boolean>()
    val isUserAuthorized: SharedFlow<Boolean> = _isUserAuthorized

    private val _isManager = MutableSharedFlow<Boolean>()
    val isManager: SharedFlow<Boolean> = _isManager

    init {
        authorize()
    }

    private fun authorize() {
        viewModelScope.launch {
            val jwt = getAccessJwt()
            val user = getUser()

            delay(300)

            if (jwt.isNullOrEmpty()) _isUserAuthorized.emit(false)
            else _isUserAuthorized.emit(true)

            if (user?.role?.let { it != Role.EMPLOYEE } == true) _isManager.emit(true)
            else _isManager.emit(false)
        }
    }

}