package com.madarsoft.userinfo

internal object Screens {

    const val USER_INPUT = "user_input"
    const val USER_OUTPUT = "user_output"

}

internal sealed class NavigationItem(val route: String) {

    data object UserInput : NavigationItem(Screens.USER_INPUT)
    data object UserOutput : NavigationItem(Screens.USER_OUTPUT)

}