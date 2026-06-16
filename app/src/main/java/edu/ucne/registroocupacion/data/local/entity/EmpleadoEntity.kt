package edu.ucne.registroocupacion.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.registroocupacion.domain.model.Empleado
import java.time.LocalDate

@Entity(tableName = "empleados")
data class EmpleadoEntity(
    @PrimaryKey(autoGenerate = true)
    val empleadoId: Int = 0,
    val fechaIngreso: LocalDate = LocalDate.now(),
    val nombres: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0
)

fun EmpleadoEntity.toDomain() = Empleado(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)

fun Empleado.toEntity() = EmpleadoEntity(
    empleadoId = empleadoId,
    fechaIngreso = fechaIngreso,
    nombres = nombres,
    sexo = sexo,
    sueldo = sueldo
)
