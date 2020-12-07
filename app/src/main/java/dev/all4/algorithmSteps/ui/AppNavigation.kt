package dev.all4.algorithmSteps.ui

import androidx.annotation.StringRes
import androidx.compose.material.Text
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.all4.algorithmSteps.R
import dev.all4.algorithmSteps.ui.screens.ConfigScreen
import dev.all4.algorithmSteps.ui.screens.HomeScreen

/**
 * Created by Livio Lopez on 12/6/20.
 */
// Define all destinations in the app
sealed class Screens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object Home : Screens("homeScreen", R.string.algorithms, Icons.Filled.Home)
    object Config : Screens("configScreen", R.string.config, Icons.Filled.Settings)
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        Screens.Home,
        Screens.Config
    )

    Scaffold(bottomBar = { DrawBottomBarItems(navController, bottomNavigationItems) }) {
        MainDestination(navController)
    }
}

@Composable
private fun MainDestination(navController: NavHostController) {
    NavHost(navController, startDestination = Screens.Home.route) {
        composable(Screens.Home.route) { HomeScreen() }
        composable(Screens.Config.route) { ConfigScreen() }
    }
}

@Composable
private fun DrawBottomBarItems(
    navController: NavHostController,
    items: List<Screens>) {

    BottomNavigation {
        val currentRoute = CurrentRoute(navController)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon) },
                label = { Text(stringResource(id = screen.resourceId)) },
                selected = currentRoute == screen.route,
                alwaysShowLabels = true,
                onClick = {
                    navController.popBackStack(navController.graph.startDestination, false)

                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}

@Composable
private fun CurrentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.arguments?.getString(KEY_ROUTE)
}