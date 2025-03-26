package ua.wied.presentation.screens.main.reports.status_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.user.Role
import ua.wied.domain.models.user.User
import java.time.LocalDateTime
import javax.inject.Inject

class ReportStatusListViewModel @Inject constructor(
) : ViewModel() {

    private val reports = MutableStateFlow<List<Report>?>(null)

    private val _todoReports = MutableStateFlow<List<Report>?>(null)
    val todoReports = _todoReports.asStateFlow()

    private val _inProgressReports = MutableStateFlow<List<Report>?>(null)
    val inProgressReports = _inProgressReports.asStateFlow()

    private val _doneReports = MutableStateFlow<List<Report>?>(null)
    val doneReports = _doneReports.asStateFlow()


    init {
        initialize()
    }

    private fun initialize() {
        reports.value = listOf(
            Report(
                id = 1,
                instructionId = 1,
                title = "Test 1",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.TODO,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            ),
            Report(
                id = 2,
                instructionId = 1,
                title = "Test 2",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.TODO,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            ),
            Report(
                id = 3,
                instructionId = 1,
                title = "Test 3",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.IN_PROGRESS,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            ),            Report(
                id = 4,
                instructionId = 1,
                title = "Test 4",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.IN_PROGRESS,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            ),            Report(
                id = 5,
                instructionId = 1,
                title = "Test 5",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.DONE,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            ),            Report(
                id = 6,
                instructionId = 1,
                title = "Test 6",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.DONE,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            ),
            Report(
                id = 7,
                instructionId = 1,
                title = "Test 7",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf("", ""),
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                status = ReportStatus.DONE,
                creator = User(
                    1,
                    "",
                    "Max",
                    "",
                    "",
                    "",
                    "",
                    Role.EMPLOYEE
                )
            )
        )

        splitReportsByStatus()
    }

    private fun splitReportsByStatus() {
        reports.value?.let { reportList ->
            _todoReports.value = reportList.filter { it.status == ReportStatus.TODO }
            _inProgressReports.value = reportList.filter { it.status == ReportStatus.IN_PROGRESS }
            _doneReports.value = reportList.filter { it.status == ReportStatus.DONE }
        }
    }


}