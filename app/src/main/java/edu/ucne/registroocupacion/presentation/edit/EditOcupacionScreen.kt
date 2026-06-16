package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOcupacionScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: EditOcupacionViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val isCompactWidth = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

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
