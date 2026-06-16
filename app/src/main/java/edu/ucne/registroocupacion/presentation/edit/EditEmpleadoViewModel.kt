package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupacion.domain.model.Empleado
import edu.ucne.registroocupacion.domain.repository.EmpleadoRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class EditEmpleadoViewModel @Inject constructor(
    private val repository: EmpleadoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var empleadoId by mutableStateOf(0)
    var fechaIngreso by mutableStateOf(LocalDate.now())
    var nombres by mutableStateOf("")
    var sexo by mutableStateOf("")
    var sueldo by mutableStateOf("")

    var errorNombres by mutableStateOf<String?>(null)
    var errorSexo by mutableStateOf<String?>(null)
    var errorSueldo by mutableStateOf<String?>(null)

    init {
        val id = savedStateHandle.get<Int>("id") ?: 0
        if (id != 0) {
            viewModelScope.launch {
                repository.getEmpleadoById(id)?.let { empleado ->
                    empleadoId = empleado.empleadoId
                    fechaIngreso = empleado.fechaIngreso
                    nombres = empleado.nombres
                    sexo = empleado.sexo
                    sueldo = empleado.sueldo.toString()
                }
            }
        }
    }

    fun save(onSuccess: () -> Unit) {
        if (!validar()) return

        viewModelScope.launch {
            val empleado = Empleado(
                empleadoId = empleadoId,
                fechaIngreso = fechaIngreso,
                nombres = nombres.trim(),
                sexo = sexo,
                sueldo = sueldo.toDoubleOrNull() ?: 0.0
            )

            if (empleadoId == 0) {
                repository.insert(empleado)
            } else {
                repository.update(empleado)
            }

            limpiar()
            onSuccess()
        }
    }

    private fun validar(): Boolean {
        var isValid = true
        errorNombres = null
        errorSexo = null
        errorSueldo = null

        if (nombres.isBlank()) {
            errorNombres = "Los nombres son obligatorios"
            isValid = false
        }
        if (sexo.isBlank()) {
            errorSexo = "El sexo es obligatorio"
            isValid = false
        }
        if (sueldo.isBlank()) {
            errorSueldo = "El sueldo es obligatorio"
            isValid = false
        } else if (sueldo.toDoubleOrNull() == null || sueldo.toDouble() <= 0) {
            errorSueldo = "Ingrese un sueldo válido mayor a cero"
            isValid = false
        }

        return isValid
    }

    private fun limpiar() {
        empleadoId = 0
        fechaIngreso = LocalDate.now()
        nombres = ""
        sexo = ""
        sueldo = ""
        errorNombres = null
        errorSexo = null
        errorSueldo = null
    }
}
