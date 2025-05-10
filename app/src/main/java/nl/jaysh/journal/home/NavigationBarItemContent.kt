package nl.jaysh.journal.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import nl.jaysh.journal.core.ui.R
import nl.jaysh.journal.navigation.Destination

sealed class NavigationBarItemContent(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
) {
    data object Dashboard : NavigationBarItemContent(
        route = Destination.DASHBOARD,
        title = R.string.bottom_navigation_dashboard,
        selectedIcon = R.drawable.ic_dashboard,
        unselectedIcon = R.drawable.ic_dashboard_outline,
    )

    data object DailyIntake : NavigationBarItemContent(
        route = Destination.DAILY_INTAKE_OVERVIEW,
        title = R.string.bottom_navigation_daily_intake,
        selectedIcon = R.drawable.ic_calendar,
        unselectedIcon = R.drawable.ic_calendar_outline,
    )

    data object Food : NavigationBarItemContent(
        route = Destination.FOOD_OVERVIEW,
        title = R.string.bottom_navigation_food_overview,
        selectedIcon = R.drawable.ic_food,
        unselectedIcon = R.drawable.ic_food_outline,
    )

    data object WeightManagement : NavigationBarItemContent(
        route = Destination.WEIGHT_MANAGEMENT,
        title = R.string.bottom_navigation_weight_management,
        selectedIcon = R.drawable.ic_scale,
        unselectedIcon = R.drawable.ic_scale_outline,
    )

    data object Settings : NavigationBarItemContent(
        route = Destination.SETTINGS,
        title = R.string.bottom_navigation_settings,
        selectedIcon = R.drawable.ic_settings,
        unselectedIcon = R.drawable.ic_settings_outline,
    )
}
