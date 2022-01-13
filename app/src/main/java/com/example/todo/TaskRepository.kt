package com.example.todo

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<Task>> = taskDao.getPriorityTask()
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: Task){
        taskDao.insert(task)
    }
    suspend fun delete(task:Task){
        taskDao.delete(task)
    }
}