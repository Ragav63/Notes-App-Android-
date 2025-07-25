package com.example.notesapproom_java.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapproom_java.databinding.NoteLayoutBinding;
import com.example.notesapproom_java.model.Note;
import com.example.notesapproom_java.view.HomeFragmentDirections;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        NoteLayoutBinding itemBinding;

        public NoteViewHolder(@NonNull NoteLayoutBinding binding) {
            super(binding.getRoot());
            this.itemBinding = binding;
        }
    }

    private final DiffUtil.ItemCallback<Note> differCallback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId() == newItem.getId() &&
                    oldItem.getNoteDesc().equals(newItem.getNoteDesc()) &&
                    oldItem.getNoteTitle().equals(newItem.getNoteTitle());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.equals(newItem);
        }
    };

    public final AsyncListDiffer<Note> differ = new AsyncListDiffer<>(this, differCallback);

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NoteLayoutBinding binding = NoteLayoutBinding.inflate(inflater, parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = differ.getCurrentList().get(position);

        holder.itemBinding.noteTitle.setText(currentNote.getNoteTitle());
        holder.itemBinding.noteDesc.setText(currentNote.getNoteDesc());

        holder.itemView.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToEditNoteFragment direction =
                    HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(currentNote);
            Navigation.findNavController(v).navigate(direction);
        });
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public AsyncListDiffer<Note> getDiffer() {
        return differ;
    }

}
