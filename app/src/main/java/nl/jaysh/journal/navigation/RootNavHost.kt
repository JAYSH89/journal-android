package nl.jaysh.journal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.jaysh.journal.feature.authentication.LoginScreen
import nl.jaysh.journal.feature.authentication.RegisterScreen
import nl.jaysh.journal.home.HomeScreen
import nl.jaysh.journal.launch.LaunchScreen
import nl.jaysh.journal.navigation.Destination.HOME
import nl.jaysh.journal.navigation.Destination.LAUNCH
import nl.jaysh.journal.navigation.Destination.LOGIN
import nl.jaysh.journal.navigation.Destination.REGISTER

@Composable
fun RootNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = LAUNCH) {
        composable(HOME) {
            HomeScreen(rootNavController = navController)
        }

        composable(LAUNCH) {
            LaunchScreen(
                navigateToRegister = {
                    navController.navigate(REGISTER) {
                        launchSingleTop = true
                    }
                },
                navigateToLogin = {
                    navController.navigate(LOGIN) {
                        launchSingleTop = true
                    }
                },
                navigateToHome = {
                    navController.navigate(HOME) {
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(HOME) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onRegisterButtonPressed = { navController.navigate(REGISTER) }
            )
        }

        composable(REGISTER) {
            RegisterScreen(onRegisterSuccess = { navController.navigateUp() })
        }
    }
}