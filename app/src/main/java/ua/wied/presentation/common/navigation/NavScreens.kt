package ua.wied.presentation.common.navigation

import kotlinx.serialization.Serializable

@Serializable
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

sealed class MainNav(route: String) : Screen(route) {
    sealed class Instructions(route: String) : MainNav(route) {
        data object InstructionDetail : Instructions("create_instruction_screen")
        data object CreateInstruction : Instructions("create_instruction_screen")
    }

    sealed class Reports(route: String) : MainNav(route)

    sealed class Evaluations(route: String) : MainNav(route)

    sealed class Profile(route: String) : MainNav(route)

    sealed class People(route: String) : MainNav(route)

    sealed class Access(route: String) : MainNav(route)
}

