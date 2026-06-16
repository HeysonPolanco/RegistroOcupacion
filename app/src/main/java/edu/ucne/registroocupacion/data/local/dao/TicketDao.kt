package edu.ucne.registroocupacion.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import edu.ucne.registroocupacion.data.local.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(ticket: TicketEntity)

    @Update
    suspend fun update(ticket: TicketEntity)

    @Delete
    suspend fun delete(ticket: TicketEntity)

    @Query("SELECT * FROM tickets WHERE TicketId = :id")
    suspend fun getTicketById(id: Int): TicketEntity?

    @Query("SELECT * FROM tickets")
    fun getTickets(): Flow<List<TicketEntity>>
}
