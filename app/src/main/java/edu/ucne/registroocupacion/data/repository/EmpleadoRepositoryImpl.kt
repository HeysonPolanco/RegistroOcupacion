package edu.ucne.registroocupacion.data.repository

import edu.ucne.registroocupacion.data.local.dao.EmpleadoDao
import edu.ucne.registroocupacion.data.local.entity.toDomain
import edu.ucne.registroocupacion.data.local.entity.toEntity
import edu.ucne.registroocupacion.domain.model.Empleado
import edu.ucne.registroocupacion.domain.repository.EmpleadoRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class EmpleadoRepositoryImpl @Inject constructor(
    private val empleadoDao: EmpleadoDao
) : EmpleadoRepository {
    override suspend fun insert(empleado: Empleado) = empleadoDao.insert(empleado.toEntity())
    override suspend fun update(empleado: Empleado) = empleadoDao.update(empleado.toEntity())
    override suspend fun delete(empleado: Empleado) = empleadoDao.delete(empleado.toEntity())
    override suspend fun getEmpleadoById(id: Int) = empleadoDao.getEmpleadoById(id)?.toDomain()
    override fun getEmpleados() = empleadoDao.getEmpleados().map { it.map { entity -> entity.toDomain() } }
}
