package com.gaurav.mynote.data

import com.gaurav.mynote.model.Note
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao:NoteDao):NoteRepository {

    override fun getAllNoteStream(): Flow<List<Note>> {
        return noteDao.getNotes()
    }

    override fun getNoteStream(id: Int): Flow<Note> {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        return noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        return noteDao.deleteNote(note)
    }

    override suspend fun updateNote(note: Note) {
        return noteDao.updateNote(note)
    }
}