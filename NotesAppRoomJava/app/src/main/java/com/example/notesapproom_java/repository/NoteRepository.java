package com.example.notesapproom_java.repository;

import androidx.lifecycle.LiveData;

import com.example.notesapproom_java.database.NoteDatabase;
import com.example.notesapproom_java.model.Note;

import java.util.List;

public class NoteRepository {

    private final NoteDatabase db;

    public NoteRepository(NoteDatabase db) {
        this.db = db;
    }

    public void insertNote(final Note note) {
        new Thread(() -> db.getNoteDao().insertNote(note)).start();
    }

    public void deleteNote(final Note note) {
        new Thread(() -> db.getNoteDao().deleteNote(note)).start();
    }

    public void updateNote(final Note note) {
        new Thread(() -> db.getNoteDao().updateNote(note)).start();
    }

    public LiveData<List<Note>> getAllNotes() {
        return db.getNoteDao().getAllNotes();
    }

    public LiveData<List<Note>> searchNotes(String query) {
        return db.getNoteDao().searchNote(query);
    }
}
