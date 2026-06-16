package edu.ucne.registroocupacion.domain.model

import java.time.LocalDate

data class Ticket(
    val ticketId: Int = 0,
    val empleadoId: Int = 0,
    val nombreEmpleado: String = "",
    val fecha: LocalDate = LocalDate.now(),
    val horasTrabajadas: Double = 0.0,
    val horasNocturnas: Double = 0.0,
    val sueldoPorHora: Double = 0.0,
    val montoHorasExtras: Double = 0.0,
    val montoTotal: Double = 0.0
)
