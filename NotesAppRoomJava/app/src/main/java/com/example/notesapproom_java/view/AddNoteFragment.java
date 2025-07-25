package com.example.notesapproom_java.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapproom_java.MainActivity;
import com.example.notesapproom_java.R;
import com.example.notesapproom_java.databinding.FragmentAddNoteBinding;
import com.example.notesapproom_java.model.Note;
import com.example.notesapproom_java.viewmodel.NoteViewModel;


public class AddNoteFragment extends Fragment implements MenuProvider {

    private FragmentAddNoteBinding addNoteBinding;
    private NoteViewModel notesViewModel;
    private View addNoteView;

    public AddNoteFragment() {
        super(R.layout.fragment_add_note);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        addNoteBinding = FragmentAddNoteBinding.inflate(inflater, container, false);
        return addNoteBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        notesViewModel = ((MainActivity) requireActivity()).getNoteViewModel();
        addNoteView = view;
    }

    private void saveNote(View view) {
        String noteTitle = addNoteBinding.addNoteTitle.getText().toString().trim();
        String noteDesc = addNoteBinding.addNoteDesc.getText().toString().trim();

        if (!noteTitle.isEmpty()) {
            Note note = new Note(0, noteTitle, noteDesc);
            notesViewModel.addNote(note);

            Toast.makeText(addNoteView.getContext(), "Note Saved", Toast.LENGTH_SHORT).show();
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack(R.id.homeFragment, false);
        } else {
            Toast.makeText(addNoteView.getContext(), "Please enter note title", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.saveMenu) {
            saveNote(addNoteView);
            return true;
        }
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        addNoteBinding = null;
    }
}