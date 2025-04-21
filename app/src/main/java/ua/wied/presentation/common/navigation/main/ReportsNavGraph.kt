package ua.wied.presentation.common.navigation.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.navigation.InstructionType
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.navigation.ReportType
import ua.wied.presentation.common.navigation.ReportsType
import ua.wied.presentation.common.navigation.TabType
import ua.wied.presentation.common.navigation.screenComposable
import ua.wied.presentation.screens.reports.ReportViewModel
import ua.wied.presentation.screens.reports.ReportsScreen
import ua.wied.presentation.screens.reports.by_status.ReportsByStatusScreen
import ua.wied.presentation.screens.reports.create.CreateReportScreen
import ua.wied.presentation.screens.reports.create.CreateReportViewModel
import ua.wied.presentation.screens.reports.create.models.CreateReportState
import ua.wied.presentation.screens.reports.detail.ReportDetailScreen
import ua.wied.presentation.screens.reports.detail.ReportDetailViewModel
import ua.wied.presentation.screens.reports.detail.models.ReportDetailState
import ua.wied.presentation.screens.reports.models.ReportsState
import ua.wied.presentation.screens.reports.status_list.ReportStatusListScreen
import ua.wied.presentation.screens.reports.status_list.ReportStatusListViewModel
import ua.wied.presentation.screens.reports.status_list.models.ReportStatusListState
import kotlin.reflect.typeOf

fun NavGraphBuilder.reportsNavGraph(
    navController: NavHostController
) {
    screenComposable<
        ReportNav.Reports,
        ReportViewModel,
        ReportsState
    >(
        tabType = TabType.START,
        stateProvider = { it.uiState }
    ) { vm, state, _ ->
        ReportsScreen(
            state = state,
            onEvent = vm::onEvent,
            navigateToStatusList = {
                navController.navigate(ReportNav.ReportStatusList(it))
            },
            navigateToCreateReport = {
                navController.navigate(ReportNav.CreateReport(it))
            }
        )
    }

    screenComposable<
        ReportNav.ReportStatusList,
        ReportStatusListViewModel,
        ReportStatusListState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Instruction>() to InstructionType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.ReportStatusList>()
        ReportStatusListScreen(
            state = state,
            onEvent = vm::onEvent,
            instruction = args.instruction,
            navigateToReportsByStatus = { list, instruction, status ->
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = list,
                    instruction = instruction,
                    status = status
                ))
            }
        )
    }

    screenComposable<
        ReportNav.ReportsByStatusList
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<List<Report>>() to ReportsType,
            typeOf<Instruction>() to InstructionType
        ),
    ) { backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.ReportsByStatusList>()
        ReportsByStatusScreen(
            navController = navController,
            reports = args.reports,
            instruction = args.instruction
        )
    }

    screenComposable<
        ReportNav.ReportDetail,
        ReportDetailViewModel,
        ReportDetailState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Report>() to ReportType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.ReportDetail>()
        ReportDetailScreen(
            state = state,
            onEvent = vm::onEvent,
            isManager = vm::isManager,
            report = args.report
        )
    }

    screenComposable<
        ReportNav.CreateReport,
        CreateReportViewModel,
        CreateReportState
    >(
        tabType = TabType.BACK,
        typeMap = mapOf(
            typeOf<Instruction>() to InstructionType
        ),
        stateProvider = { it.uiState }
    ) { vm, state, backStackEntry ->
        val args = backStackEntry.toRoute<ReportNav.CreateReport>()
        CreateReportScreen(
            state = state,
            instruction = args.instruction,
            onEvent = vm::onEvent,
            isFieldsEmpty = vm::isFieldsEmpty,
            navigateToReports = {
                navController.navigate(ReportNav.Reports)
            }
        )
    }
}