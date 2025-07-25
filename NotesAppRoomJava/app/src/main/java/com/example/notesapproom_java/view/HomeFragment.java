package com.example.notesapproom_java.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesapproom_java.MainActivity;
import com.example.notesapproom_java.R;
import com.example.notesapproom_java.adapter.NoteAdapter;
import com.example.notesapproom_java.databinding.FragmentHomeBinding;
import com.example.notesapproom_java.model.Note;
import com.example.notesapproom_java.viewmodel.NoteViewModel;

import java.util.List;


public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, MenuProvider {

    private FragmentHomeBinding binding;
    private NoteViewModel noteViewModel;
    private NoteAdapter noteAdapter;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MenuHost menuHost = (MenuHost) requireActivity();
        menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        noteViewModel = ((MainActivity) requireActivity()).getNoteViewModel();

        setUpHomeRecyclerView();

        binding.addNoteFab.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_addNoteFragment)
        );
    }

    private void updateUI(List<Note> notes) {
        if (notes != null && !notes.isEmpty()) {
            binding.emptyNotesImage.setVisibility(View.GONE);
            binding.homeRecyclerView.setVisibility(View.VISIBLE);
        } else {
            binding.emptyNotesImage.setVisibility(View.VISIBLE);
            binding.homeRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setUpHomeRecyclerView() {
        noteAdapter = new NoteAdapter();
        binding.homeRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        );
        binding.homeRecyclerView.setHasFixedSize(true);
        binding.homeRecyclerView.setAdapter(noteAdapter);

        if (getActivity() != null) {
            noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
                noteAdapter.getDiffer().submitList(notes);
                updateUI(notes);
            });
        }
    }

    private void searchNote(String query) {
        String searchQuery = "%" + query;

        noteViewModel.searchNote(searchQuery).observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.getDiffer().submitList(notes);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null) {
            searchNote(newText);
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menu.clear();
        menuInflater.inflate(R.menu.home_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchMenu);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}