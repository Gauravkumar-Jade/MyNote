package com.gaurav.mynote.data

import com.gaurav.mynote.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    /**
     * Retrieve all the notes from the the given data source.
     */
    fun getAllNoteStream(): Flow<List<Note>>

    /**
     * Retrieve an note from the given data source that matches with the [id].
     */
    fun getNoteStream(id:Int):Flow<Note>

    /**
     * Insert note in the data source
     */
    suspend fun insertNote(note:Note)

    /**
     * Delete note in the data source
     */
    suspend fun deleteNote(note:Note)

    /**
     * Update note in the data source
     */
    suspend fun updateNote(note:Note)
}