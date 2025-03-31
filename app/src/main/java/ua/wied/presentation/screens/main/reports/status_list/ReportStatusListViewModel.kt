package ua.wied.presentation.screens.main.reports.status_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ua.wied.domain.models.report.ImageUrl
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.models.report.Report
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
                userId = 1,
                title = "Test 1",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf(
                    ImageUrl(1, "https://randomwordgenerator.com/img/picture-generator/53e4d4404b54a914f1dc8460962e33791c3ad6e04e507749742c78d69e48c1_640.jpg")
                ),
                status = ReportStatus.TODO,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            ),
            Report(
                id = 2,
                instructionId = 1,
                userId = 1,
                title = "Test 2",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = listOf(
                    ImageUrl(2, "https://randomwordgenerator.com/img/picture-generator/53e4d4404b54a914f1dc8460962e33791c3ad6e04e507749742c78d69e48c1_640.jpg"),
                    ImageUrl(3, "https://randomwordgenerator.com/img/picture-generator/53e4d4404b54a914f1dc8460962e33791c3ad6e04e507749742c78d69e48c1_640.jpg")
                ),
                status = ReportStatus.TODO,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            ),
            Report(
                id = 3,
                instructionId = 1,
                userId = 2,
                title = "Зламана ручка",
                info = "Відлетіла дверна ручка, не можливо двері відчинити",
                imageUrls = listOf(
                    ImageUrl(4, "https://randomwordgenerator.com/img/picture-generator/53e4d4404b54a914f1dc8460962e33791c3ad6e04e507749742c78d69e48c1_640.jpg"),
                    ImageUrl(5, "https://randomwordgenerator.com/img/picture-generator/53e4d4404b54a914f1dc8460962e33791c3ad6e04e507749742c78d69e48c1_640.jpg"),
                    ImageUrl(6, "https://randomwordgenerator.com/img/picture-generator/53e4d4404b54a914f1dc8460962e33791c3ad6e04e507749742c78d69e48c1_640.jpg")
                ),
                status = ReportStatus.TODO,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            ),
            Report(
                id = 4,
                instructionId = 1,
                userId = 1,
                title = "Test 4",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = emptyList(),
                status = ReportStatus.IN_PROGRESS,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            ),
            Report(
                id = 5,
                instructionId = 1,
                userId = 1,
                title = "Test 5",
                info = "Відлетіла дверна ручка, не можливо двері відчинити",
                imageUrls = emptyList(),
                status = ReportStatus.DONE,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            ),
            Report(
                id = 6,
                instructionId = 1,
                userId = 1,
                title = "Test 6",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = emptyList(),
                status = ReportStatus.DONE,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
            ),
            Report(
                id = 7,
                instructionId = 1,
                userId = 1,
                title = "Test 7",
                info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                imageUrls = emptyList(),
                status = ReportStatus.DONE,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now()
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