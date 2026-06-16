package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOcupacionScreen(
    viewModel: EditOcupacionViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (viewModel.ocupacionId == 0) "Nueva Ocupación" else "Editar Ocupación")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.descripcion,
                onValueChange = {
                    viewModel.descripcion = it
                    viewModel.errorDescripcion = null
                },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.errorDescripcion != null,
                supportingText = {
                    if (viewModel.errorDescripcion != null) {
                        Text(
                            text = viewModel.errorDescripcion!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = viewModel.sueldo,
                onValueChange = {
                    viewModel.sueldo = it
                    viewModel.errorSueldo = null
                },
                label = { Text("Sueldo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.errorSueldo != null,
                supportingText = {
                    if (viewModel.errorSueldo != null) {
                        Text(
                            text = viewModel.errorSueldo!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            if (viewModel.generalError != null) {
                Text(
                    text = viewModel.generalError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

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
