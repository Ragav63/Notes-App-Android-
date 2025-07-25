package com.example.notesapproom_java.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.notesapproom_java.repository.NoteRepository;

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private final Application app;
    private final NoteRepository noteRepository;

    public NoteViewModelFactory(Application app, NoteRepository noteRepository) {
        this.app = app;
        this.noteRepository = noteRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NoteViewModel(app, noteRepository);
    }
}
