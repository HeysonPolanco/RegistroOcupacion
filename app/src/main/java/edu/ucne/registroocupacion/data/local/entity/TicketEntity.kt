package edu.ucne.registroocupacion.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.registroocupacion.domain.model.Ticket
import java.time.LocalDate

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TicketId")
    val ticketId: Int = 0,
    @ColumnInfo(name = "EmpleadoId")
    val empleadoId: Int = 0,
    @ColumnInfo(name = "Fecha")
    val fecha: LocalDate = LocalDate.now(),
    @ColumnInfo(name = "HorasTrabajadas")
    val horasTrabajadas: Double = 0.0,
    @ColumnInfo(name = "HorasNocturnas")
    val horasNocturnas: Double = 0.0,
    @ColumnInfo(name = "SueldoPorHora")
    val sueldoPorHora: Double = 0.0,
    @ColumnInfo(name = "MontoHorasExtras")
    val montoHorasExtras: Double = 0.0,
    @ColumnInfo(name = "MontoTotal")
    val montoTotal: Double = 0.0
)

fun TicketEntity.toDomain(nombreEmpleado: String = "") = Ticket(
    ticketId = ticketId,
    empleadoId = empleadoId,
    nombreEmpleado = nombreEmpleado,
    fecha = fecha,
    horasTrabajadas = horasTrabajadas,
    horasNocturnas = horasNocturnas,
    sueldoPorHora = sueldoPorHora,
    montoHorasExtras = montoHorasExtras,
    montoTotal = montoTotal
)

fun Ticket.toEntity() = TicketEntity(
    ticketId = ticketId,
    empleadoId = empleadoId,
    fecha = fecha,
    horasTrabajadas = horasTrabajadas,
    horasNocturnas = horasNocturnas,
    sueldoPorHora = sueldoPorHora,
    montoHorasExtras = montoHorasExtras,
    montoTotal = montoTotal
)
