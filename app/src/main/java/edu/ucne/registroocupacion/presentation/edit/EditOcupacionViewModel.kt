package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupacion.domain.model.Ocupacion
import edu.ucne.registroocupacion.domain.repository.OcupacionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditOcupacionViewModel @Inject constructor(
    private val repository: OcupacionRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var ocupacionId by mutableStateOf(0)
    var descripcion by mutableStateOf("")
    var sueldo by mutableStateOf("")

    var errorDescripcion by mutableStateOf<String?>(null)
    var errorSueldo by mutableStateOf<String?>(null)
    var generalError by mutableStateOf<String?>(null)

    init {
        val id = savedStateHandle.get<Int>("id") ?: 0
        if (id != 0) {
            viewModelScope.launch {
                repository.getOcupacionById(id)?.let { ocupacion ->
                    ocupacionId = ocupacion.ocupacionId
                    descripcion = ocupacion.descripcion
                    sueldo = ocupacion.sueldo.toString()
                }
            }
        }
    }
    fun save(onSuccess: () -> Unit) {
        if (!validar()) return

        viewModelScope.launch {
            if (repository.existsByDescripcion(descripcion, ocupacionId)) {
                errorDescripcion = "Ya existe una ocupación con esta descripción"
                return@launch
            }

            val ocupacion = Ocupacion(
                ocupacionId = ocupacionId,
                descripcion = descripcion.trim(),
                sueldo = sueldo.toDoubleOrNull() ?: 0.0
            )

            if (ocupacionId == 0) {
                repository.insert(ocupacion)
            } else {
                repository.update(ocupacion)
            }

            limpiar()
            onSuccess()
        }
    }

    private fun validar(): Boolean {
        var isValid = true
        errorDescripcion = null
        errorSueldo = null
        generalError = null

        if (descripcion.isBlank()) {
            errorDescripcion = "La descripción es obligatoria"
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
        ocupacionId = 0
        descripcion = ""
        sueldo = ""
        errorDescripcion = null
        errorSueldo = null
        generalError = null
    }
}