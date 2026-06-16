package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTicketScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: EditTicketViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val isCompactWidth = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact
    val empleados by viewModel.empleados.collectAsStateWithLifecycle()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (viewModel.ticketId == 0) "Nuevo Registro Overtime" else "Editar Registro Overtime") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .widthIn(max = if (isCompactWidth) 600.dp else 800.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Empleado Selector
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = empleados.find { it.empleadoId == viewModel.empleadoId }?.nombres ?: "Seleccione un empleado",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Empleado") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        isError = viewModel.errorEmpleado != null,
                        supportingText = { if (viewModel.errorEmpleado != null) Text(viewModel.errorEmpleado!!) }
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        empleados.forEach { empleado ->
                            DropdownMenuItem(
                                text = { Text(empleado.nombres) },
                                onClick = {
                                    viewModel.onEmpleadoSelected(empleado)
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = viewModel.horasTrabajadas,
                    onValueChange = { viewModel.onHorasChanged(it) },
                    label = { Text("Horas Semanales") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorHoras != null,
                    supportingText = { if (viewModel.errorHoras != null) Text(viewModel.errorHoras!!) }
                )

                OutlinedTextField(
                    value = viewModel.horasNocturnas,
                    onValueChange = { viewModel.onHorasNocturnasChanged(it) },
                    label = { Text("Horas Nocturnas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                // Results Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Cálculos", style = MaterialTheme.typography.titleMedium)
                        HorizontalDivider()
                        ResultRow("Sueldo por Hora:", String.format("$%.2f", viewModel.sueldoPorHora))
                        ResultRow("Monto Horas Extras:", String.format("$%.2f", viewModel.montoHorasExtras))
                        ResultRow("Monto Total Semana:", String.format("$%.2f", viewModel.montoTotal), style = MaterialTheme.typography.titleLarge)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.save(onSuccess = onNavigateBack) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar Registro")
                }
            }
        }
    }
}

@Composable
fun ResultRow(label: String, value: String, style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyLarge) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, style = style)
        Text(text = value, style = style, color = MaterialTheme.colorScheme.primary)
    }
}
