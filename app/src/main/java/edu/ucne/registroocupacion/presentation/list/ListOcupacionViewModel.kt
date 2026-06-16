package edu.ucne.registroocupacion.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupacion.domain.model.Ocupacion
import edu.ucne.registroocupacion.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOcupacionViewModel @Inject constructor(
    private val repository: OcupacionRepository
) : ViewModel() {

    val ocupaciones = repository.getOcupaciones()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun delete(ocupacion: Ocupacion) {
        viewModelScope.launch {
            repository.delete(ocupacion)
        }
    }
}