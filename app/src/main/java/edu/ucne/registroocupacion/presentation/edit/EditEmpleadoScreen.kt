package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmpleadoScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: EditEmpleadoViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val isCompactWidth = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (viewModel.empleadoId == 0) "Nuevo Empleado" else "Editar Empleado")
                },
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
                OutlinedTextField(
                    value = viewModel.nombres,
                    onValueChange = {
                        viewModel.nombres = it
                        viewModel.errorNombres = null
                    },
                    label = { Text("Nombres") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorNombres != null,
                    supportingText = {
                        if (viewModel.errorNombres != null) {
                            Text(viewModel.errorNombres!!, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                OutlinedTextField(
                    value = viewModel.fechaIngreso.format(DateTimeFormatter.ISO_LOCAL_DATE),
                    onValueChange = { },
                    label = { Text("Fecha Ingreso") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showDatePicker = true },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.CalendarMonth, contentDescription = "Seleccionar fecha")
                        }
                    }
                )

                if (showDatePicker) {
                    val datePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = viewModel.fechaIngreso.toEpochDay() * 24 * 60 * 60 * 1000
                    )
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    viewModel.fechaIngreso = LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000))
                                }
                                showDatePicker = false
                            }) {
                                Text("Aceptar")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDatePicker = false }) {
                                Text("Cancelar")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                // Sexo
                Column {
                    Text("Sexo", style = MaterialTheme.typography.labelMedium)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = viewModel.sexo == "M",
                            onClick = { viewModel.sexo = "M"; viewModel.errorSexo = null }
                        )
                        Text("Masculino")
                        Spacer(modifier = Modifier.width(16.dp))
                        RadioButton(
                            selected = viewModel.sexo == "F",
                            onClick = { viewModel.sexo = "F"; viewModel.errorSexo = null }
                        )
                        Text("Femenino")
                    }
                    if (viewModel.errorSexo != null) {
                        Text(
                            viewModel.errorSexo!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                OutlinedTextField(
                    value = viewModel.sueldo,
                    onValueChange = {
                        viewModel.sueldo = it
                        viewModel.errorSueldo = null
                    },
                    label = { Text("Sueldo") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    isError = viewModel.errorSueldo != null,
                    supportingText = {
                        if (viewModel.errorSueldo != null) {
                            Text(viewModel.errorSueldo!!, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        viewModel.save(onSuccess = onNavigateBack)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar")
                }
            }
        }
    }
}
