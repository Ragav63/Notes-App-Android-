package com.example.notesapproom_java.view;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesapproom_java.MainActivity;
import com.example.notesapproom_java.R;
import com.example.notesapproom_java.databinding.FragmentEditNoteBinding;
import com.example.notesapproom_java.model.Note;
import com.example.notesapproom_java.viewmodel.NoteViewModel;


public class EditNoteFragment extends Fragment implements MenuProvider {

    private FragmentEditNoteBinding binding;
    private NoteViewModel notesViewModel;
    private Note currentNote;

    public EditNoteFragment() {
        super(R.layout.fragment_edit_note);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditNoteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        notesViewModel = ((MainActivity) requireActivity()).getNoteViewModel();

        if (getArguments() != null) {
            EditNoteFragmentArgs args = EditNoteFragmentArgs.fromBundle(getArguments());
            currentNote = args.getNote();
        }

        binding.editNoteTitle.setText(currentNote.getNoteTitle());
        binding.editNoteDesc.setText(currentNote.getNoteDesc());

        binding.editNoteFab.setOnClickListener(v -> {
            String noteTitle = binding.editNoteTitle.getText().toString().trim();
            String noteDesc = binding.editNoteDesc.getText().toString().trim();

            if (!noteTitle.isEmpty()) {
                Note updatedNote = new Note(currentNote.getId(), noteTitle, noteDesc);
                notesViewModel.updateNote(updatedNote);
                Navigation.findNavController(v).popBackStack(R.id.homeFragment, false);
            } else {
                Toast.makeText(getContext(), "Please enter note title", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteNote() {
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete note")
                .setMessage("Do you want to delete this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    notesViewModel.deleteNote(currentNote);
                    Toast.makeText(getContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
                    NavController navController = NavHostFragment.findNavController(this);
                    navController.popBackStack(R.id.homeFragment, false);
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.menu_delete, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.deleteMenu) {
            deleteNote();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}