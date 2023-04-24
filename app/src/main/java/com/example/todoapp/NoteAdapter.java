package com.example.todoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.database.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteviewHolder> {
    private List<Note> notes;
    private OnNoteItemClick onNoteItemClick;

    public NoteAdapter(List<Note> notes, OnNoteItemClick onNoteItemClick) {
        this.notes = notes;
        this.onNoteItemClick = onNoteItemClick;
    }

    @NonNull
    @Override
    public NoteviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row, parent, false);
        return new NoteviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteviewHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getNoteTitle());
        holder.textViewDescription.setText(note.getNoteDescription());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class NoteviewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textViewTitle;
        AppCompatTextView textViewDescription;

        public NoteviewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textview_title);
            textViewDescription = itemView.findViewById(R.id.textview_description);

            itemView.setOnClickListener(view -> onNoteItemClick.onNoteClick(getAdapterPosition()));
        }
    }

    public interface OnNoteItemClick {
        void onNoteClick(int position);
    }
}
