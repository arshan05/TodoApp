package com.example.todo

import kotlinx.coroutines.flow.Flow
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY priority ASC")
    fun getPriorityTask(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()
}