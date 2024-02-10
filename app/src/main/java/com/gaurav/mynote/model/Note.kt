package com.gaurav.mynote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
