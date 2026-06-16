package edu.ucne.registroocupacion.domain.repository

import edu.ucne.registroocupacion.domain.model.Empleado
import kotlinx.coroutines.flow.Flow

interface EmpleadoRepository {
    suspend fun insert(empleado: Empleado)
    suspend fun update(empleado: Empleado)
    suspend fun delete(empleado: Empleado)
    suspend fun getEmpleadoById(id: Int): Empleado?
    fun getEmpleados(): Flow<List<Empleado>>
}
