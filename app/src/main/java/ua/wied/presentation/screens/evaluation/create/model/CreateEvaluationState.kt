package ua.wied.presentation.screens.evaluation.create.model

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.domain.models.evaluation.ItemEvaluation
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.base.BaseNetworkResponseState
import java.time.LocalDateTime

data class CreateEvaluationState (
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val createResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    val employees: List<User> = emptyList(),
    val employee: User? = null,
    val instructions: List<Instruction> = emptyList(),
    val instruction: Instruction? = null,
    val info: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val itemsEvaluation: List<ItemEvaluation> = emptyList(),
    val finalEvaluation: Double = 0.0,
): BaseNetworkResponseState