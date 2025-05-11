package ua.wied.presentation.screens.people

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ua.wied.domain.models.user.User
import ua.wied.domain.usecases.DeleteEmployeeUseCase
import ua.wied.domain.usecases.GetEmployeesUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.people.model.PeopleEvent
import ua.wied.presentation.screens.people.model.PeopleState
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getEmployees: GetEmployeesUseCase,
    private val deleteEmployeeUseCase: DeleteEmployeeUseCase
): BaseViewModel<PeopleState, PeopleEvent>(PeopleState()) {
    val employeeFlow = MutableStateFlow<List<User>>(emptyList())

    init {
        initialize()
    }

    override fun onEvent(event: PeopleEvent) {
        when (event) {
            is PeopleEvent.SearchChanged -> {
                updateState {
                    it.copy(
                        search = event.value,
                        employees = filterEmployees(event.value)
                    )
                }
            }
            is PeopleEvent.DeletePressed -> deleteEmployee(event.value)
            is PeopleEvent.Refresh -> { initialize(true) }
        }
    }

    private fun filterEmployees(query: String): List<User> {
        if (query.isEmpty()) return employeeFlow.value

        val lowerQuery = query.trim().lowercase()
        return employeeFlow.value.filter { user ->
            user.name.lowercase().contains(lowerQuery)
        }
    }

    private fun initialize(isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getEmployees() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { employees ->
                employeeFlow.update { employees }
                updateState {
                    it.copy(
                        employees = employeeFlow.value,
                        isEmpty = employees.isEmpty(),
                        isNotInternetConnection = false
                    )
                }
            },
            onRefresh = { value ->
                if (isRefresh) {
                    if (!value) delay(100)
                    updateState { it.copy(isRefreshing = value ) }
                }
            }
        )
    }

    private fun deleteEmployee(employeeId: Int) {
        collectNetworkRequest(
            apiCall = {
                deleteEmployeeUseCase(employeeId)
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {  },
            onSuccess = {
                initialize(true)
            }
        )
    }
}