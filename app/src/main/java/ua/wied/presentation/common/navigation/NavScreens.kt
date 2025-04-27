package ua.wied.presentation.common.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import ua.wied.R
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report

@Serializable
sealed class Screen

@Serializable
sealed class GlobalNav : Screen() {
    @Serializable
    data object Auth : GlobalNav()

    @Serializable
    data class Main(val isManager: Boolean) : GlobalNav()
}

@Serializable
sealed class AuthNav : Screen() {
    @Serializable
    data object SignIn : AuthNav()

    @Serializable
    data object SignUp : AuthNav()
}

@Serializable
sealed class MainNav : Screen()

@Serializable
sealed class InstructionNav : MainNav() {
    @Serializable
    data object Instructions: InstructionNav()

    @Serializable
    data class InstructionDetail(val instruction: Instruction) : InstructionNav()

    @Serializable
    data class InstructionElementDetail(val element: Element) : InstructionNav()

    @Serializable
    data class CreateInstruction(val orderNum: Int, val folderId: Int) : InstructionNav()

    @Serializable
    data class CreateElement(val orderNum: Int) : InstructionNav()
}

@Serializable
sealed class ReportNav : MainNav() {
    @Serializable
    data object Reports: ReportNav()

    @Serializable
    data class CreateReport(val instruction: Instruction): ReportNav()

    @Serializable
    data class ReportStatusList(val instruction: Instruction): ReportNav()

    @Serializable
    data class ReportsByStatusList(val reports: List<Report>, val instruction: Instruction, val status: String): ReportNav()

    @Serializable
    data class ReportDetail(val report: Report): ReportNav()
}

@Serializable
sealed class EvaluationNav : MainNav() {
    @Serializable
    data object Evaluations: EvaluationNav()
}

@Serializable
sealed class PeopleNav : MainNav() {
    @Serializable
    data object People: PeopleNav()
}

@Serializable
sealed class AccessNav : MainNav() {
    @Serializable
    data object Accesses: AccessNav()
}

@Serializable
sealed class ProfileNav: MainNav() {
    @Serializable
    data object Profile: ProfileNav()
}

@Serializable
sealed class BottomBarScreen(@StringRes val label: Int, @DrawableRes val icon: Int, val route: MainNav) {
    @Serializable
    data object Instructions : BottomBarScreen(R.string.instructions, R.drawable.icon_camcorder, InstructionNav.Instructions)

    @Serializable
    data object Reports : BottomBarScreen(R.string.reports, R.drawable.icon_note_and_pencil, ReportNav.Reports)

    @Serializable
    data object Evaluations : BottomBarScreen(R.string.evaluations, R.drawable.icon_star, EvaluationNav.Evaluations)

    @Serializable
    data object Accesses : BottomBarScreen(R.string.accesses, R.drawable.icon_add_person, EvaluationNav.Evaluations)

    @Serializable
    data object Profile : BottomBarScreen(R.string.profile, R.drawable.icon_account, ProfileNav.Profile)
}
