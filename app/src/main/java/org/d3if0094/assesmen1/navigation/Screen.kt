package org.d3if0094.assesmen1.navigation

sealed class Screen(val route: String) {
    object Home : Screen("mainScreen")
    object About : Screen("aboutScreen")
}

