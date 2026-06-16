package edu.ucne.registroocupacion.presentation.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registroocupacion.domain.model.Empleado
import edu.ucne.registroocupacion.domain.model.Ticket
import edu.ucne.registroocupacion.domain.repository.EmpleadoRepository
import edu.ucne.registroocupacion.domain.repository.TicketRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class EditTicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val empleadoRepository: EmpleadoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var ticketId by mutableStateOf(0)
    var empleadoId by mutableStateOf(0)
    var fecha by mutableStateOf(LocalDate.now())
    var horasTrabajadas by mutableStateOf("")
    var horasNocturnas by mutableStateOf("")

    var sueldoPorHora by mutableStateOf(0.0)
    var montoHorasExtras by mutableStateOf(0.0)
    var montoTotal by mutableStateOf(0.0)

    var errorEmpleado by mutableStateOf<String?>(null)
    var errorHoras by mutableStateOf<String?>(null)

    val empleados = empleadoRepository.getEmpleados()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        val id = savedStateHandle.get<Int>("id") ?: 0
        if (id != 0) {
            viewModelScope.launch {
                ticketRepository.getTicketById(id)?.let { ticket ->
                    ticketId = ticket.ticketId
                    empleadoId = ticket.empleadoId
                    fecha = ticket.fecha
                    horasTrabajadas = ticket.horasTrabajadas.toString()
                    horasNocturnas = ticket.horasNocturnas.toString()
                    sueldoPorHora = ticket.sueldoPorHora
                    montoHorasExtras = ticket.montoHorasExtras
                    montoTotal = ticket.montoTotal
                }
            }
        }
    }

    fun onEmpleadoSelected(empleado: Empleado) {
        empleadoId = empleado.empleadoId
        // Sueldo diario = SueldoMensual / 23.53
        // Sueldo hora = SueldoDiario / 8
        sueldoPorHora = empleado.sueldo / 23.53 / 8
        calculate()
    }

    fun onHorasChanged(horas: String) {
        horasTrabajadas = horas
        calculate()
    }

    fun onHorasNocturnasChanged(horas: String) {
        horasNocturnas = horas
        calculate()
    }

    private fun calculate() {
        val totalHours = horasTrabajadas.toDoubleOrNull() ?: 0.0
        val nightHours = horasNocturnas.toDoubleOrNull() ?: 0.0
        
        if (sueldoPorHora <= 0 || totalHours <= 0) {
            montoHorasExtras = 0.0
            montoTotal = 0.0
            return
        }

        val normalHours = min(44.0, totalHours)
        val extra35Hours = min(24.0, max(0.0, totalHours - 44.0))
        val extra100Hours = max(0.0, totalHours - 68.0)

        val montoNormal = normalHours * sueldoPorHora
        val monto35 = extra35Hours * sueldoPorHora * 1.35
        val monto100 = extra100Hours * sueldoPorHora * 2.0

        // Night Bonus calculation (+15% on the base/extra rate)
        var remainingNight = nightHours
        val night100 = min(extra100Hours, remainingNight)
        remainingNight -= night100
        val night35 = min(extra35Hours, remainingNight)
        remainingNight -= night35
        val nightNormal = min(normalHours, remainingNight)

        val bonusNocturno = (nightNormal * 1.0 + night35 * 1.35 + night100 * 2.0) * sueldoPorHora * 0.15

        montoHorasExtras = monto35 + monto100 + bonusNocturno
        montoTotal = montoNormal + montoHorasExtras
    }

    fun save(onSuccess: () -> Unit) {
        if (!validar()) return

        viewModelScope.launch {
            val ticket = Ticket(
                ticketId = ticketId,
                empleadoId = empleadoId,
                fecha = fecha,
                horasTrabajadas = horasTrabajadas.toDoubleOrNull() ?: 0.0,
                horasNocturnas = horasNocturnas.toDoubleOrNull() ?: 0.0,
                sueldoPorHora = sueldoPorHora,
                montoHorasExtras = montoHorasExtras,
                montoTotal = montoTotal
            )

            if (ticketId == 0) {
                ticketRepository.insert(ticket)
            } else {
                ticketRepository.update(ticket)
            }

            onSuccess()
        }
    }

    private fun validar(): Boolean {
        var isValid = true
        errorEmpleado = null
        errorHoras = null

        if (empleadoId == 0) {
            errorEmpleado = "Debe seleccionar un empleado"
            isValid = false
        }
        if (horasTrabajadas.toDoubleOrNull() == null || horasTrabajadas.toDouble() <= 0) {
            errorHoras = "Ingrese una cantidad de horas válida"
            isValid = false
        }

        return isValid
    }
}
