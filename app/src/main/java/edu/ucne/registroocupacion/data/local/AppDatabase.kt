package edu.ucne.registroocupacion.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.registroocupacion.data.local.dao.EmpleadoDao
import edu.ucne.registroocupacion.data.local.dao.OcupacionDao
import edu.ucne.registroocupacion.data.local.entity.EmpleadoEntity
import edu.ucne.registroocupacion.data.local.entity.OcupacionEntity

@Database(
    entities = [
        OcupacionEntity::class,
        EmpleadoEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadoDao(): EmpleadoDao
}
