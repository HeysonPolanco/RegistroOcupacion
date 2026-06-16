package edu.ucne.registroocupacion.data.repository

import edu.ucne.registroocupacion.data.local.dao.OcupacionDao
import edu.ucne.registroocupacion.data.local.entity.toDomain
import edu.ucne.registroocupacion.data.local.entity.toEntity
import edu.ucne.registroocupacion.domain.model.Ocupacion
import edu.ucne.registroocupacion.domain.repository.OcupacionRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OcupacionRepositoryImpl @Inject constructor(
    private val ocupacionDao: OcupacionDao
) : OcupacionRepository {
    override suspend fun insert(ocupacion: Ocupacion) = ocupacionDao.insert(ocupacion.toEntity())
    override suspend fun update(ocupacion: Ocupacion) = ocupacionDao.update(ocupacion.toEntity())
    override suspend fun delete(ocupacion: Ocupacion) = ocupacionDao.delete(ocupacion.toEntity())
    override suspend fun getOcupacionById(id: Int) = ocupacionDao.getOcupacionById(id)?.toDomain()
    override fun getOcupaciones() = ocupacionDao.getOcupaciones().map { it.map { entity -> entity.toDomain() } }
    override suspend fun existsByDescripcion(descripcion: String, id: Int) = ocupacionDao.existsByDescripcion(descripcion, id)
}
