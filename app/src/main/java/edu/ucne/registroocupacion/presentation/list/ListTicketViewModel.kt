package edu.ucne.registroocupacion.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupacion.domain.model.Ticket
import edu.ucne.registroocupacion.domain.repository.TicketRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListTicketViewModel @Inject constructor(
    private val repository: TicketRepository
) : ViewModel() {

    val tickets = repository.getTickets()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun delete(ticket: Ticket) {
        viewModelScope.launch {
            repository.delete(ticket)
        }
    }
}
