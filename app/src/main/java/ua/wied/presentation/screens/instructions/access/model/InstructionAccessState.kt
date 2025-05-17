package ua.wied.presentation.screens.instructions.access.model
import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class InstructionAccessState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val instructionPublicUrl: String = "",
    val employees: List<User> = emptyList(),
    val instruction: Instruction? = null
): BaseNetworkResponseState