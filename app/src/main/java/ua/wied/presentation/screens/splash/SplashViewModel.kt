package ua.wied.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import ua.wied.domain.usecases.GetAccessJwtUseCase
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAccessJwtUseCase: GetAccessJwtUseCase
) : ViewModel() {

    private val _isUserAuthorized = MutableSharedFlow<Boolean>()
    val isUserAuthorized: SharedFlow<Boolean> = _isUserAuthorized

    init {
        authorize()
    }

    private fun authorize() {
        viewModelScope.launch {
            val jwt = getAccessJwtUseCase()

            delay(300)

            if (jwt.isNullOrEmpty()) _isUserAuthorized.emit(false)
            else _isUserAuthorized.emit(true)
        }
    }
}