package ua.wied.presentation.common.navigation

import androidx.annotation.DrawableRes
import ua.wied.R

sealed class NavScreen

sealed class Screen(val route: String)

sealed class Global(route: String) : Screen(route) {
    data object Auth : Global("auth_screen")
    data object Main : Global("main_screen")
}

sealed class AuthNav(route: String) : Screen(route){
    data object SignIn : AuthNav("sign_in_screen")
    data object SignUp : AuthNav("sign_Up_screen")
}


sealed class MainNav(route: String) : Screen(route)
sealed class InstructionNav(route: String) : MainNav(route) {
    data object Instructions: InstructionNav("instructions_screen")
    data object InstructionDetail : InstructionNav("instruction_detail_screen")
    data object CreateInstruction : InstructionNav("create_instruction_screen")
}
sealed class ReportNav(route: String) : MainNav(route) {
    data object Reports: ReportNav("reports_screen")
}
sealed class EvaluationNav(route: String) : MainNav(route) {
    data object Evaluations: EvaluationNav("evaluations_screen")
}
sealed class PeopleNav(route: String) : MainNav(route) {
    data object People: PeopleNav("people_screen")
}
sealed class AccessNav(route: String) : MainNav(route){
    data object Accesses: AccessNav("accesses_screen")
}
sealed class ProfileNav(route: String): MainNav(route) {
    data object Profile: ProfileNav("profile_screen")
}

enum class BottomBarScreens(val label: String, @DrawableRes val icon: Int, val route: String) {
    Instructions("Instructions", R.drawable.icon_camcorder, InstructionNav.Instructions.route),
    Reports("Reports", R.drawable.icon_pencil, ReportNav.Reports.route),
    Evaluations("Evaluations", R.drawable.icon_star, EvaluationNav.Evaluations.route),
    Profile("Profile", R.drawable.icon_account, ProfileNav.Profile.route)
}
