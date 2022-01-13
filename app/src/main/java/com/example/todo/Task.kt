package com.example.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
data class Task(val task: String, val priority: Int,val dateS: String, @PrimaryKey(autoGenerate =
false) val id:Int? = null)