package edu.ucne.registroocupacion.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.registroocupacion.domain.model.Ocupacion

@Entity(
    tableName = "ocupaciones",
    indices = [androidx.room.Index(value = ["Descripcion"], unique = true)]
)
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "OcupacionId")
    val ocupacionId: Int = 0,
    @ColumnInfo(name = "Descripcion")
    val descripcion: String = "",
    @ColumnInfo(name = "Sueldo")
    val sueldo: Double = 0.0
)

fun OcupacionEntity.toDomain() = Ocupacion(
    ocupacionId = ocupacionId,
    descripcion = descripcion,
    sueldo = sueldo
)

fun Ocupacion.toEntity() = OcupacionEntity(
    ocupacionId = ocupacionId,
    descripcion = descripcion,
    sueldo = sueldo
)
