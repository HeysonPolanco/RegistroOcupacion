package edu.ucne.registroocupacion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.ucne.registroocupacion.data.local.entity.OcupacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ocupacion: OcupacionEntity)

    @Update
    suspend fun update(ocupacion: OcupacionEntity)

    @Delete
    suspend fun delete(ocupacion: OcupacionEntity)

    @Query("SELECT * FROM ocupaciones WHERE OcupacionId = :id")
    suspend fun getOcupacionById(id: Int): OcupacionEntity?

    @Query("SELECT * FROM ocupaciones")
    fun getOcupaciones(): Flow<List<OcupacionEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM ocupaciones WHERE Descripcion = :descripcion AND OcupacionId != :id)")
    suspend fun existsByDescripcion(descripcion: String, id: Int): Boolean
}
