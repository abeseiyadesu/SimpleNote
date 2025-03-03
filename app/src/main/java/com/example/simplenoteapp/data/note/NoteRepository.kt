package com.example.simplenoteapp.data.note

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {
    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) = noteDao.insert(note)
    suspend fun update(note: Note) = noteDao.update(note)
    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
}