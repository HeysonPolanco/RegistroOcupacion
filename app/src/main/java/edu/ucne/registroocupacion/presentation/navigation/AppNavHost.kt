package edu.ucne.registroocupacion.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import edu.ucne.registroocupacion.presentation.edit.EditEmpleadoScreen
import edu.ucne.registroocupacion.presentation.edit.EditOcupacionScreen
import edu.ucne.registroocupacion.presentation.edit.EditTicketScreen
import edu.ucne.registroocupacion.presentation.list.ListEmpleadoScreen
import edu.ucne.registroocupacion.presentation.list.ListOcupacionScreen
import edu.ucne.registroocupacion.presentation.list.ListTicketScreen

@Composable
fun AppNavHost(windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                windowSizeClass = windowSizeClass,
                onNavigateToOcupaciones = { navController.navigate("list_ocupacion") },
                onNavigateToEmpleados = { navController.navigate("list_empleado") },
                onNavigateToOvertime = { navController.navigate("list_ticket") }
            )
        }

        // Ocupaciones
        composable("list_ocupacion") {
            ListOcupacionScreen(
                windowSizeClass = windowSizeClass,
                onNavigateToCreate = { navController.navigate("edit_ocupacion/0") },
                onNavigateToEdit = { id -> navController.navigate("edit_ocupacion/$id") }
            )
        }
        composable(
            route = "edit_ocupacion/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditOcupacionScreen(
                windowSizeClass = windowSizeClass,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // Empleados
        composable("list_empleado") {
            ListEmpleadoScreen(
                windowSizeClass = windowSizeClass,
                onNavigateToCreate = { navController.navigate("edit_empleado/0") },
                onNavigateToEdit = { id -> navController.navigate("edit_empleado/$id") }
            )
        }
        composable(
            route = "edit_empleado/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditEmpleadoScreen(
                windowSizeClass = windowSizeClass,
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // Tickets (Overtime)
        composable("list_ticket") {
            ListTicketScreen(
                windowSizeClass = windowSizeClass,
                onNavigateToCreate = { navController.navigate("edit_ticket/0") },
                onNavigateToEdit = { id -> navController.navigate("edit_ticket/$id") }
            )
        }
        composable(
            route = "edit_ticket/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            EditTicketScreen(
                windowSizeClass = windowSizeClass,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}

@Composable
fun HomeScreen(
    windowSizeClass: WindowSizeClass,
    onNavigateToOcupaciones: () -> Unit,
    onNavigateToEmpleados: () -> Unit,
    onNavigateToOvertime: () -> Unit
) {
    val isCompactWidth = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sistema de Gestión Laboral",
            style = if (isCompactWidth) MaterialTheme.typography.headlineMedium else MaterialTheme.typography.displaySmall,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Spacer(modifier = Modifier.height(48.dp))

        if (isCompactWidth) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HomeButton(text = "Ocupaciones", onClick = onNavigateToOcupaciones)
                HomeButton(text = "Empleados", onClick = onNavigateToEmpleados)
                HomeButton(text = "Gestión Overtime", onClick = onNavigateToOvertime)
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeButton(text = "Ocupaciones", onClick = onNavigateToOcupaciones)
                HomeButton(text = "Empleados", onClick = onNavigateToEmpleados)
                HomeButton(text = "Gestión Overtime", onClick = onNavigateToOvertime)
            }
        }
    }
}

@Composable
fun HomeButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.widthIn(min = 180.dp).height(56.dp)
    ) {
        Text(text)
    }
}
