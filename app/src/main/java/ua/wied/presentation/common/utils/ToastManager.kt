package ua.wied.presentation.common.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.*

class ToastManager {
    private val toastChannel = Channel<Int>(Channel.CONFLATED)
    val toastMessages: Flow<Int> = toastChannel.receiveAsFlow()

    private val toastMutex = Mutex()

    suspend fun showToast(@StringRes messageResId: Int) {
        if (toastMutex.tryLock()) {
            toastChannel.send(messageResId)
        }
    }

    suspend fun processToastMessages(context: Context) {
        toastMessages.collect { messageResId ->
            Toast.makeText(context, context.getString(messageResId), Toast.LENGTH_SHORT).show()
            delay(2000)
            toastMutex.unlock()
        }
    }
}
