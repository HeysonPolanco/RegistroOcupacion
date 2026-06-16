package edu.ucne.registroocupacion.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.ucne.registroocupacion.presentation.edit.EditOcupacionScreen
import edu.ucne.registroocupacion.presentation.list.ListOcupacionScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            ListOcupacionScreen(
                onNavigateToCreate = {
                    navController.navigate("edit/0")
                },
                onNavigateToEdit = { id ->
                    navController.navigate("edit/$id")
                }
            )
        }

        composable(
            route = "edit/{id}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType }
            )
        ) {
            EditOcupacionScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}