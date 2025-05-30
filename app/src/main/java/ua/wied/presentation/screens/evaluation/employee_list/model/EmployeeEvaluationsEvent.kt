package ua.wied.presentation.screens.evaluation.employee_list.model

import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.user.User

sealed class EmployeeEvaluationsEvent {
    data class LoadData(val employee: User): EmployeeEvaluationsEvent()
    data class SearchChanged(val value: String): EmployeeEvaluationsEvent()
    data class DateRangeChanged(val value: DateRange): EmployeeEvaluationsEvent()
    data object Refresh : EmployeeEvaluationsEvent()
}