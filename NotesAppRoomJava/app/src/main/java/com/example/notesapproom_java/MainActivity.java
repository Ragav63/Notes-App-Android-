package com.example.notesapproom_java;

import android.os.Bundle;

import com.example.notesapproom_java.database.NoteDatabase;
import com.example.notesapproom_java.repository.NoteRepository;
import com.example.notesapproom_java.viewmodel.NoteViewModel;
import com.example.notesapproom_java.viewmodel.NoteViewModelFactory;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Optional: Edge-to-edge support (not directly available like Kotlin DSL)
        // WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViewModel();
    }

    private void setupViewModel() {
        NoteRepository noteRepository = new NoteRepository(NoteDatabase.getInstance(this));
        NoteViewModelFactory viewModelFactory = new NoteViewModelFactory(getApplication(), noteRepository);

        noteViewModel = new ViewModelProvider(this, viewModelFactory).get(NoteViewModel.class);
    }

    public NoteViewModel getNoteViewModel() {
        return noteViewModel;
    }
}