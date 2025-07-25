package com.example.notesapproom_java.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapproom_java.model.Note;
import com.example.notesapproom_java.repository.NoteRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository noteRepository;
    private final ExecutorService executorService;

    public NoteViewModel(@NonNull Application application, NoteRepository noteRepository) {
        super(application);
        this.noteRepository = noteRepository;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void addNote(Note note) {
        executorService.execute(() -> noteRepository.insertNote(note));
    }

    public void deleteNote(Note note) {
        executorService.execute(() -> noteRepository.deleteNote(note));
    }

    public void updateNote(Note note) {
        executorService.execute(() -> noteRepository.updateNote(note));
    }

    public LiveData<List<Note>> getAllNotes() {
        return noteRepository.getAllNotes();
    }

    public LiveData<List<Note>> searchNote(String query) {
        return noteRepository.searchNotes(query);
    }
}

