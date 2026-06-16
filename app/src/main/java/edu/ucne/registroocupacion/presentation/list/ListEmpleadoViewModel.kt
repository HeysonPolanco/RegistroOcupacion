package edu.ucne.registroocupacion.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupacion.domain.model.Empleado
import edu.ucne.registroocupacion.domain.repository.EmpleadoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEmpleadoViewModel @Inject constructor(
    private val repository: EmpleadoRepository
) : ViewModel() {

    val empleados = repository.getEmpleados()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun delete(empleado: Empleado) {
        viewModelScope.launch {
            repository.delete(empleado)
        }
    }
}
