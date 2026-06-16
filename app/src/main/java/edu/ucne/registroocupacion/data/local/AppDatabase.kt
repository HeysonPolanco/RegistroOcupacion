package edu.ucne.registroocupacion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.registroocupacion.data.local.dao.EmpleadoDao
import edu.ucne.registroocupacion.data.local.dao.OcupacionDao
import edu.ucne.registroocupacion.data.local.dao.TicketDao
import edu.ucne.registroocupacion.data.local.entity.EmpleadoEntity
import edu.ucne.registroocupacion.data.local.entity.OcupacionEntity
import edu.ucne.registroocupacion.data.local.entity.TicketEntity

@Database(
    entities = [
        OcupacionEntity::class,
        EmpleadoEntity::class,
        TicketEntity::class
    ],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun ticketDao(): TicketDao
}
