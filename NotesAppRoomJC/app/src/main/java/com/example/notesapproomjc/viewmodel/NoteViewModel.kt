package com.example.notesapproomjc.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapproom.model.Note
import com.example.notesapproomjc.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(app: Application, private val noteRepository: NoteRepository): AndroidViewModel(app) {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        noteRepository.getAllNotes().observeForever {
            _notes.value = it
        }
    }
    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    fun getAllNotes() {
        loadNotes()
    }

    fun searchNote(query: String?) {
        noteRepository.searchNotes(query).observeForever {
            _notes.value = it
        }
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
        loadNotes()
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
        loadNotes()
    }



}