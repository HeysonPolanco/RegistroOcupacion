package edu.ucne.registroocupacion.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registroocupacion.data.local.AppDatabase
import edu.ucne.registroocupacion.data.local.dao.EmpleadoDao
import edu.ucne.registroocupacion.data.local.dao.OcupacionDao
import edu.ucne.registroocupacion.data.repository.EmpleadoRepositoryImpl
import edu.ucne.registroocupacion.data.repository.OcupacionRepositoryImpl
import edu.ucne.registroocupacion.domain.repository.EmpleadoRepository
import edu.ucne.registroocupacion.domain.repository.OcupacionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "Ocupacion.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideOcupacionDao(appDatabase: AppDatabase): OcupacionDao {
        return appDatabase.ocupacionDao()
    }

    @Provides
    fun provideEmpleadoDao(appDatabase: AppDatabase): EmpleadoDao {
        return appDatabase.empleadoDao()
    }

    @Provides
    fun provideOcupacionRepository(ocupacionDao: OcupacionDao): OcupacionRepository {
        return OcupacionRepositoryImpl(ocupacionDao)
    }

    @Provides
    fun provideEmpleadoRepository(empleadoDao: EmpleadoDao): EmpleadoRepository {
        return EmpleadoRepositoryImpl(empleadoDao)
    }
}
