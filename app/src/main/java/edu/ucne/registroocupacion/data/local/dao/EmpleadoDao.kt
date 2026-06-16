package edu.ucne.registroocupacion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.ucne.registroocupacion.data.local.entity.EmpleadoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleadoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(empleado: EmpleadoEntity)

    @Update
    suspend fun update(empleado: EmpleadoEntity)

    @Delete
    suspend fun delete(empleado: EmpleadoEntity)

    @Query("SELECT * FROM empleados WHERE empleadoId = :id")
    suspend fun getEmpleadoById(id: Int): EmpleadoEntity?

    @Query("SELECT * FROM empleados")
    fun getEmpleados(): Flow<List<EmpleadoEntity>>
}
