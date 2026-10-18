package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CineSonicDao {
    @Query("SELECT * FROM projects WHERE id = :id LIMIT 1")
    fun getProject(id: String): Flow<ProjectEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectEntity)

    @Query("SELECT * FROM shots WHERE projectId = :projectId ORDER BY shotNumber ASC")
    fun getShotsForProject(projectId: String): Flow<List<ShotEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShots(shots: List<ShotEntity>)

    @Query("SELECT * FROM usage_ledger WHERE projectId = :projectId ORDER BY timestamp DESC")
    fun getUsageLedger(projectId: String): Flow<List<UsageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsage(entry: UsageEntity)
}
