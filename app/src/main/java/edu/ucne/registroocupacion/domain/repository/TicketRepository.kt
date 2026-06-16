package edu.ucne.registroocupacion.domain.repository

import edu.ucne.registroocupacion.domain.model.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    suspend fun insert(ticket: Ticket)
    suspend fun update(ticket: Ticket)
    suspend fun delete(ticket: Ticket)
    suspend fun getTicketById(id: Int): Ticket?
    fun getTickets(): Flow<List<Ticket>>
}
