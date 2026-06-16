package edu.ucne.registroocupacion.data.repository

import edu.ucne.registroocupacion.data.local.dao.EmpleadoDao
import edu.ucne.registroocupacion.data.local.dao.TicketDao
import edu.ucne.registroocupacion.data.local.entity.toDomain
import edu.ucne.registroocupacion.data.local.entity.toEntity
import edu.ucne.registroocupacion.domain.model.Ticket
import edu.ucne.registroocupacion.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketDao: TicketDao,
    private val empleadoDao: EmpleadoDao
) : TicketRepository {
    override suspend fun insert(ticket: Ticket) = ticketDao.insert(ticket.toEntity())
    override suspend fun update(ticket: Ticket) = ticketDao.update(ticket.toEntity())
    override suspend fun delete(ticket: Ticket) = ticketDao.delete(ticket.toEntity())
    
    override suspend fun getTicketById(id: Int): Ticket? {
        val entity = ticketDao.getTicketById(id) ?: return null
        val empleado = empleadoDao.getEmpleadoById(entity.empleadoId)
        return entity.toDomain(empleado?.nombres ?: "")
    }

    override fun getTickets(): Flow<List<Ticket>> {
        return ticketDao.getTickets().map { entities ->
            entities.map { entity ->
                val empleado = empleadoDao.getEmpleadoById(entity.empleadoId)
                entity.toDomain(empleado?.nombres ?: "")
            }
        }
    }
}
