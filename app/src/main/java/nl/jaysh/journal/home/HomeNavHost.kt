package nl.jaysh.journal.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import nl.jaysh.journal.feature.dailyintake.detail.DailyIntakeDetailScreen
import nl.jaysh.journal.feature.dailyintake.overview.DailyIntakeOverviewScreen
import nl.jaysh.journal.feature.dashboard.DashboardScreen
import nl.jaysh.journal.feature.food.detail.FoodDetailScreen
import nl.jaysh.journal.feature.food.overview.FoodOverviewScreen
import nl.jaysh.journal.feature.settings.SettingsScreen
import nl.jaysh.journal.feature.weightmanagement.WeightManagementScreen
import nl.jaysh.journal.navigation.Destination.DAILY_INTAKE_DETAIL
import nl.jaysh.journal.navigation.Destination.DAILY_INTAKE_OVERVIEW
import nl.jaysh.journal.navigation.Destination.DASHBOARD
import nl.jaysh.journal.navigation.Destination.FOOD_DETAIL
import nl.jaysh.journal.navigation.Destination.FOOD_OVERVIEW
import nl.jaysh.journal.navigation.Destination.SETTINGS
import nl.jaysh.journal.navigation.Destination.WEIGHT_MANAGEMENT

@Composable
fun HomeNavHost(rootNavController: NavController, homeNavController: NavHostController) {
    NavHost(navController = homeNavController, startDestination = DASHBOARD) {
        composable(DASHBOARD) {
            DashboardScreen()
        }

        composable(DAILY_INTAKE_OVERVIEW) {
            DailyIntakeOverviewScreen()
        }

        composable(DAILY_INTAKE_DETAIL) {
            DailyIntakeDetailScreen()
        }

        composable(FOOD_OVERVIEW) {
            FoodOverviewScreen(onClickAdd = { homeNavController.navigate(FOOD_DETAIL) })
        }

        composable(FOOD_DETAIL) {
            FoodDetailScreen(onSubmit = { homeNavController.navigateUp() })
        }

        composable(WEIGHT_MANAGEMENT) {
            WeightManagementScreen()
        }

        composable(SETTINGS) {
            SettingsScreen()
        }
    }
}