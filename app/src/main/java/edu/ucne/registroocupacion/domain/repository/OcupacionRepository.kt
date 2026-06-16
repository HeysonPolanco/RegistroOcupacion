package edu.ucne.registroocupacion.domain.repository

import edu.ucne.registroocupacion.domain.model.Ocupacion
import kotlinx.coroutines.flow.Flow

interface OcupacionRepository {
    suspend fun insert(ocupacion: Ocupacion)
    suspend fun update(ocupacion: Ocupacion)
    suspend fun delete(ocupacion: Ocupacion)
    suspend fun getOcupacionById(id: Int): Ocupacion?
    fun getOcupaciones(): Flow<List<Ocupacion>>
    suspend fun existsByDescripcion(descripcion: String, id: Int): Boolean
}
